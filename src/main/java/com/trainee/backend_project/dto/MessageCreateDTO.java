package com.trainee.backend_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MessageCreateDTO {
    @NotNull
    public Long propertyId;

    @NotNull
    public Long receiverId;

    @NotBlank
    public String messageContent;
}
