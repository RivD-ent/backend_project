package com.trainee.backend_project.dto;

import jakarta.validation.constraints.Size;

public class UserUpdateDTO {
    public String name;

    @Size(min = 6)
    public String password;

    public String phoneNumber;
}
