package com.trainee.backend_project.dto;

import java.time.OffsetDateTime;

public class MessageResponseDTO {
    public Long id;
    public Long propertyId;
    public Long senderId;
    public Long receiverId;
    public String messageContent;
    public Boolean isRead;
    public OffsetDateTime sentAt;
}
