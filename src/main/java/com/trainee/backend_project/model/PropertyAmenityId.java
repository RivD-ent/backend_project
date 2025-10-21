package com.trainee.backend_project.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyAmenityId implements Serializable {
    private Long property;
    private Long amenity;
}
