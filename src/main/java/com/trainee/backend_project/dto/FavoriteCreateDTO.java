package com.trainee.backend_project.dto;

import jakarta.validation.constraints.NotNull;

public class FavoriteCreateDTO {
    @NotNull
    public Long propertyId;
}
