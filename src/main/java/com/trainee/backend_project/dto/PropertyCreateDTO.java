package com.trainee.backend_project.dto;

import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PropertyCreateDTO {
    @NotBlank
    public String title;
    public String description;
    @NotNull
    public BigDecimal price;
    public String address;
    @NotBlank
    public String city;
    @NotBlank
    public String district;
    @NotBlank
    public String operationType;
    @NotBlank
    public String propertyType;
    public Integer bedrooms;
    public Integer bathrooms;
    public Integer areaSqMeters;
    public String contactName;
    public String contactPhone;
    public String googleMapsUrl;
    public Long ownerId;
    public List<Long> amenityIds;
    public List<String> imageUrls;
}
