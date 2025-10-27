package com.trainee.backend_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateDTO {
    @NotBlank
    public String name;

    @NotBlank
    @Email
    public String email;

    @NotBlank
    @Size(min = 6)
    public String password;

    public String phoneNumber;
}
