package com.trainee.backend_project.dto;

import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.constraints.NotBlank;

public class PropertyUpdateDTO {
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
    public List<Long> amenityIds;
    public List<String> imageUrls;
}
