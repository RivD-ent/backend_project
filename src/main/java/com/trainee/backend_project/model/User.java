package com.trainee.backend_project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, unique = true, length = 100)
	private String email;

	@Column(nullable = false, length = 255)
	private String password;

	@Column(length = 20)
	private String phoneNumber;

	@Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
	private OffsetDateTime updatedAt;

	// Relaciones
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private List<Property> properties;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Favorite> favorites;

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private List<Message> sentMessages;

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	private List<Message> receivedMessages;

	// Lombok genera getters, setters, constructores
}
