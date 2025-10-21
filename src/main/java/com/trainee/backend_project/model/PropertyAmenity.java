package com.trainee.backend_project.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "property_amenities")
@IdClass(PropertyAmenityId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyAmenity {
    @Id
    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Id
    @ManyToOne
    @JoinColumn(name = "amenity_id", nullable = false)
    private Amenity amenity;

    // Lombok genera getters, setters, constructores
}
