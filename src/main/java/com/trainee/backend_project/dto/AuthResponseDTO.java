package com.trainee.backend_project.dto;

public class AuthResponseDTO {
    public String token;
    public long expiresIn;
    public String tokenType = "Bearer";
}
