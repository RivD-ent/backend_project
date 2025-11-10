package com.trainee.backend_project.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.trainee.backend_project.dto.PropertyListDTO;
import com.trainee.backend_project.dto.PropertyResponseDTO;
import com.trainee.backend_project.model.Amenity;
import com.trainee.backend_project.model.Property;
import com.trainee.backend_project.model.PropertyAmenity;

public final class PropertyMapper {

    private PropertyMapper() {
        // Utility class
    }

    public static PropertyResponseDTO toResponseDto(Property property) {
        if (property == null) {
            return null;
        }
        PropertyResponseDTO dto = new PropertyResponseDTO();
        dto.id = property.getId();
        dto.title = property.getTitle();
        dto.description = property.getDescription();
        dto.price = property.getPrice();
        dto.address = property.getAddress();
        dto.city = property.getCity();
        dto.district = property.getDistrict();
        dto.operationType = property.getOperationType() != null ? property.getOperationType().name() : null;
        dto.propertyType = property.getPropertyType() != null ? property.getPropertyType().name() : null;
        dto.bedrooms = property.getBedrooms();
        dto.bathrooms = property.getBathrooms();
        dto.areaSqMeters = property.getAreaSqMeters();
        dto.contactName = property.getContactName();
        dto.contactPhone = property.getContactPhone();
        dto.googleMapsUrl = property.getGoogleMapsUrl();
        dto.status = property.getStatus() != null ? property.getStatus().name() : null;
        dto.ownerId = property.getOwner() != null ? property.getOwner().getId() : null;
        dto.createdAt = property.getCreatedAt();
        dto.updatedAt = property.getUpdatedAt();
        if (property.getImages() != null) {
            dto.imageUrls = property.getImages().stream()
                .map(image -> image.getImageUrl())
                .toList();
        }
        dto.amenities = extractAmenityNames(property);
        return dto;
    }

    public static PropertyListDTO toListDto(Property property) {
        if (property == null) {
            return null;
        }
        PropertyListDTO dto = new PropertyListDTO();
        dto.id = property.getId();
        dto.title = property.getTitle();
        dto.price = property.getPrice();
        dto.city = property.getCity();
        dto.district = property.getDistrict();
        if (property.getImages() != null && !property.getImages().isEmpty()) {
            dto.firstImageUrl = property.getImages().get(0).getImageUrl();
        }
        dto.amenities = extractAmenityNames(property);
        return dto;
    }

    private static List<String> extractAmenityNames(Property property) {
        if (property == null || property.getAmenities() == null) {
            return Collections.emptyList();
        }
        return property.getAmenities().stream()
            .map(PropertyAmenity::getAmenity)
            .filter(Objects::nonNull)
            .map(Amenity::getName)
            .filter(Objects::nonNull)
            .toList();
    }
}
