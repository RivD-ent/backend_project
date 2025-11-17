package com.trainee.backend_project.controller;

import com.trainee.backend_project.dto.ConversationSummaryDTO;
import com.trainee.backend_project.dto.MessageCreateDTO;
import com.trainee.backend_project.dto.MessageResponseDTO;
import com.trainee.backend_project.service.MessageService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> sendMessage(
        @Valid @RequestBody MessageCreateDTO dto,
        Principal principal
    ) {
        MessageResponseDTO response = messageService.sendMessage(dto, principal);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationSummaryDTO>> getConversations(Principal principal) {
        List<ConversationSummaryDTO> summaries = messageService.getConversationSummaries(principal);
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/conversations/{propertyId}/{otherUserId}")
    public ResponseEntity<List<MessageResponseDTO>> getConversationMessages(
        @PathVariable Long propertyId,
        @PathVariable Long otherUserId,
        Principal principal
    ) {
        List<MessageResponseDTO> messages = messageService.getConversationMessages(propertyId, otherUserId, principal);
        return ResponseEntity.ok(messages);
    }
}
