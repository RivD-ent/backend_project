package com.trainee.backend_project.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "amenities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    // Lombok genera getters, setters, constructores
}
