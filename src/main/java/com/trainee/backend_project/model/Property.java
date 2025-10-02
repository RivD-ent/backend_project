package com.trainee.backend_project.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 255)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal price;

	@Column(length = 255)
	private String address;

	@Column(length = 100)
	private String city;

	@Enumerated(EnumType.STRING)
	@Column(name = "operation_type", nullable = false)
	private OperationType operationType;

	@Enumerated(EnumType.STRING)
	@Column(name = "property_type", nullable = false)
	private PropertyType propertyType;

	private Integer bedrooms;
	private Integer bathrooms;
	private Integer areaSqMeters;

	@Column(length = 100)
	private String contactName;

	@Column(length = 20)
	private String contactPhone;

	@Column(length = 512)
	private String googleMapsUrl;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private PropertyStatus status = PropertyStatus.AVAILABLE;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;

	@Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
	private OffsetDateTime updatedAt;

	// Relaciones
	@OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
	private List<PropertyImage> images;

	@OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
	private List<Favorite> favorites;

	@OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
	private List<PropertyAmenity> amenities;

	@OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
	private List<Message> messages;

	// Enums
	public enum OperationType { SALE, RENTAL }
	public enum PropertyType { APARTMENT, HOUSE, OFFICE, LAND }
	public enum PropertyStatus { AVAILABLE, SOLD, RENTED }

	// Lombok genera getters, setters, constructores
}
