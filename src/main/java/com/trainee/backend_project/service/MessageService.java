package com.trainee.backend_project.service;

import com.trainee.backend_project.model.Message;
import com.trainee.backend_project.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public Message sendMessage(/*MessageDTO dto*/) {
        // Implementar lógica para enviar mensaje
        return null;
    }

    public List<Message> getInboxForUser(Integer userId) {
        // Implementar lógica para obtener conversaciones
        return null;
    }

    @Transactional
    public void markConversationAsRead(Integer conversationId) {
        // Implementar lógica para marcar como leída
    }
}
