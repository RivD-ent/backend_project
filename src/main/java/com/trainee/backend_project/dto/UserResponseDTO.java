package com.trainee.backend_project.dto;

import java.time.OffsetDateTime;

public class UserResponseDTO {
    public Long id;
    public String name;
    public String email;
    public String phoneNumber;
    public OffsetDateTime createdAt;
    public OffsetDateTime updatedAt;
}
