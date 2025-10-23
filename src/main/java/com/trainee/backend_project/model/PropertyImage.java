package com.trainee.backend_project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "property_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @Column(length = 255)
    private String description;

    @Column(name = "uploaded_at", columnDefinition = "TIMESTAMPTZ DEFAULT NOW()", insertable = false, updatable = false)
    private OffsetDateTime uploadedAt;

    // Lombok genera getters, setters, constructores
}
