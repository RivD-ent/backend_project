package com.trainee.backend_project.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PropertyResponseDTO {
    public Long id;
    public String title;
    public String description;
    public BigDecimal price;
    public String address;
    public String city;
    public String district;
    public String operationType;
    public String propertyType;
    public Integer bedrooms;
    public Integer bathrooms;
    public Integer areaSqMeters;
    public String contactName;
    public String contactPhone;
    public String googleMapsUrl;
    public String status;
    public Long ownerId;
    public OffsetDateTime createdAt;
    public OffsetDateTime updatedAt;
    public List<String> imageUrls;
    public List<String> amenities;
}
