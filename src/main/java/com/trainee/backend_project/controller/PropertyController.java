package com.trainee.backend_project.controller;

import com.trainee.backend_project.dto.PropertyCreateDTO;
import com.trainee.backend_project.dto.PropertyResponseDTO;
import com.trainee.backend_project.model.Property;
import com.trainee.backend_project.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyResponseDTO> create(@RequestBody PropertyCreateDTO dto) {
        Property p = propertyService.createProperty(dto);
        PropertyResponseDTO res = toDto(p);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDTO> getById(@PathVariable Long id) {
        Optional<Property> p = propertyService.findPropertyById(id);
        if (p.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDto(p.get()));
    }

    private PropertyResponseDTO toDto(Property p) {
        PropertyResponseDTO r = new PropertyResponseDTO();
        r.id = p.getId();
        r.title = p.getTitle();
        r.description = p.getDescription();
        r.price = p.getPrice();
        r.address = p.getAddress();
        r.city = p.getCity();
        r.operationType = p.getOperationType() != null ? p.getOperationType().name() : null;
        r.propertyType = p.getPropertyType() != null ? p.getPropertyType().name() : null;
        r.bedrooms = p.getBedrooms();
        r.bathrooms = p.getBathrooms();
        r.areaSqMeters = p.getAreaSqMeters();
        r.contactName = p.getContactName();
        r.contactPhone = p.getContactPhone();
        r.googleMapsUrl = p.getGoogleMapsUrl();
        r.status = p.getStatus() != null ? p.getStatus().name() : null;
        r.ownerId = p.getOwner() != null ? p.getOwner().getId() : null;
        r.createdAt = p.getCreatedAt();
        r.updatedAt = p.getUpdatedAt();
        if (p.getImages() != null) {
            r.imageUrls = p.getImages().stream().map(i -> i.getImageUrl()).toList();
        }
        return r;
    }
}
