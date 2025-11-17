package com.trainee.backend_project.service;

import com.trainee.backend_project.dto.ConversationSummaryDTO;
import com.trainee.backend_project.dto.MessageCreateDTO;
import com.trainee.backend_project.dto.MessageResponseDTO;
import com.trainee.backend_project.model.Message;
import com.trainee.backend_project.model.Property;
import com.trainee.backend_project.model.User;
import com.trainee.backend_project.repository.MessageRepository;
import com.trainee.backend_project.repository.PropertyRepository;
import com.trainee.backend_project.repository.UserRepository;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public MessageService(
        MessageRepository messageRepository,
        UserRepository userRepository,
        PropertyRepository propertyRepository
    ) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }

    @Transactional
    public MessageResponseDTO sendMessage(MessageCreateDTO dto, Principal principal) {
        User sender = resolveUser(principal);
        Property property = propertyRepository.findById(dto.propertyId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "propiedad no encontrada"));
        User receiver = userRepository.findById(dto.receiverId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "destinatario no encontrado"));

        if (Objects.equals(receiver.getId(), sender.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no puedes enviarte mensajes a ti mismo");
        }

        User propertyOwner = property.getOwner();
        if (propertyOwner == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "la propiedad no tiene propietario asignado");
        }

        boolean senderIsOwner = Objects.equals(propertyOwner.getId(), sender.getId());
        boolean receiverIsOwner = Objects.equals(propertyOwner.getId(), receiver.getId());

        if (!senderIsOwner && !receiverIsOwner) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "solo el propietario y el interesado pueden participar en la conversación");
        }

        if (!senderIsOwner && receiverIsOwner) {
            // non-owner contacting owner is valid
        } else if (senderIsOwner && receiverIsOwner) {
            // both owner? should not happen because receiver != sender but both owner means duplicate account
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no puedes enviarte mensajes a ti mismo");
        } else if (senderIsOwner && !receiverIsOwner) {
            // owner replying to interested user: allow
        } else {
            // receiver is neither owner nor sender's counterpart (e.g. non-owner messaging another non-owner)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "destinatario inválido para esta conversación");
        }

        String sanitizedContent = dto.messageContent.trim();
        if (sanitizedContent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "el mensaje no puede estar vacío");
        }

        Message message = new Message();
        message.setProperty(property);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessageContent(sanitizedContent);
        message.setIsRead(Boolean.FALSE);
        message.setSentAt(OffsetDateTime.now());

        Message saved = messageRepository.save(message);
        return toMessageResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<ConversationSummaryDTO> getConversationSummaries(Principal principal) {
        User currentUser = resolveUser(principal);
        List<Message> messages = messageRepository.findBySenderIdOrReceiverId(currentUser.getId(), currentUser.getId());

        // Group messages by property and counterpart to build conversation summaries.
        Map<ConversationKey, ConversationAccumulator> grouped = new HashMap<>();
        for (Message message : messages) {
            User otherUser = Objects.equals(message.getSender().getId(), currentUser.getId())
                ? message.getReceiver()
                : message.getSender();

            Long conversationPropertyId = message.getProperty() != null ? message.getProperty().getId() : 0L;
            String propertyTitle = message.getProperty() != null ? message.getProperty().getTitle() : null;
            OffsetDateTime sentAt = message.getSentAt() != null ? message.getSentAt() : OffsetDateTime.MIN;

            ConversationKey key = new ConversationKey(conversationPropertyId, otherUser.getId());
            ConversationAccumulator accumulator = grouped.computeIfAbsent(key, ignored -> {
                ConversationSummaryDTO summary = new ConversationSummaryDTO();
                summary.propertyId = conversationPropertyId;
                summary.propertyTitle = propertyTitle;
                summary.otherUserId = otherUser.getId();
                summary.otherUserName = otherUser.getName();
                summary.lastMessageContent = message.getMessageContent();
                return new ConversationAccumulator(summary, sentAt, message.getId());
            });

            if (sentAt.isAfter(accumulator.lastSentAt)
                || (sentAt.equals(accumulator.lastSentAt) && compareIds(message.getId(), accumulator.lastMessageId) > 0)) {
                accumulator.summary.lastMessageContent = message.getMessageContent();
                accumulator.lastSentAt = sentAt;
                accumulator.lastMessageId = message.getId();
                if (propertyTitle != null) {
                    accumulator.summary.propertyTitle = propertyTitle;
                }
            }
        }

        return grouped.values().stream()
            .sorted((a, b) -> {
                int timeCompare = b.lastSentAt.compareTo(a.lastSentAt);
                if (timeCompare != 0) {
                    return timeCompare;
                }
                return compareIds(b.lastMessageId, a.lastMessageId);
            })
            .map(accumulator -> accumulator.summary)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageResponseDTO> getConversationMessages(Long propertyId, Long otherUserId, Principal principal) {
        User currentUser = resolveUser(principal);
        User otherUser = userRepository.findById(otherUserId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "usuario no encontrado"));

        Long propertyFilter = propertyId;
        if (propertyFilter != null && propertyFilter <= 0) {
            propertyFilter = null;
        }

        List<Message> messages = messageRepository.findConversationMessages(propertyFilter, currentUser.getId(), otherUser.getId());
        return messages.stream()
            .sorted(Comparator.comparing(Message::getSentAt, Comparator.nullsFirst(OffsetDateTime::compareTo))
                .thenComparing(Message::getId, Comparator.nullsFirst(Long::compareTo)))
            .map(this::toMessageResponseDTO)
            .collect(Collectors.toList());
    }

    private MessageResponseDTO toMessageResponseDTO(Message message) {
        MessageResponseDTO dto = new MessageResponseDTO();
        dto.id = message.getId();
        dto.propertyId = message.getProperty() != null ? message.getProperty().getId() : 0L;
        dto.senderId = message.getSender() != null ? message.getSender().getId() : null;
        dto.receiverId = message.getReceiver() != null ? message.getReceiver().getId() : null;
        dto.messageContent = message.getMessageContent();
        dto.isRead = message.getIsRead();
        dto.sentAt = message.getSentAt();
        return dto;
    }

    private User resolveUser(Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "usuario no autenticado");
        }
        return userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "usuario no encontrado"));
    }

    private int compareIds(Long left, Long right) {
        if (left == null && right == null) {
            return 0;
        }
        if (left == null) {
            return -1;
        }
        if (right == null) {
            return 1;
        }
        return left.compareTo(right);
    }

    private record ConversationKey(Long propertyId, Long otherUserId) { }

    private static final class ConversationAccumulator {
        private final ConversationSummaryDTO summary;
        private OffsetDateTime lastSentAt;
        private Long lastMessageId;

        private ConversationAccumulator(ConversationSummaryDTO summary, OffsetDateTime lastSentAt, Long lastMessageId) {
            this.summary = summary;
            this.lastSentAt = lastSentAt;
            this.lastMessageId = lastMessageId;
        }
    }
}
