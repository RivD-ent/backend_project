package com.trainee.backend_project.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "favorites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private OffsetDateTime createdAt;

    // Lombok genera getters, setters, constructores
}
