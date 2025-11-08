package com.trainee.backend_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequestDTO {
    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String password;
}
