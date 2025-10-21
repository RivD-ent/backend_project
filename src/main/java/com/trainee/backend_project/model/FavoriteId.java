package com.trainee.backend_project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteId implements Serializable {
    private Long user;
    private Long property;
}