package com.trainee.backend_project.service;

import com.trainee.backend_project.model.Property;
import com.trainee.backend_project.model.User;
import com.trainee.backend_project.model.Amenity;
import com.trainee.backend_project.model.PropertyImage;
import com.trainee.backend_project.model.PropertyAmenity;
import com.trainee.backend_project.dto.PropertyCreateDTO;
import com.trainee.backend_project.dto.PropertyUpdateDTO;
import com.trainee.backend_project.dto.SearchFiltersDTO;
import com.trainee.backend_project.repository.PropertyRepository;
import com.trainee.backend_project.repository.UserRepository;
import com.trainee.backend_project.repository.AmenityRepository;
import com.trainee.backend_project.repository.PropertyImageRepository;
import com.trainee.backend_project.repository.PropertyAmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AmenityRepository amenityRepository;
    @Autowired
    private PropertyImageRepository propertyImageRepository;
    @Autowired
    private PropertyAmenityRepository propertyAmenityRepository;


    @Transactional
    public Property createProperty(PropertyCreateDTO dto, User owner) {
        Property property = new Property();
        property.setTitle(dto.title);
        property.setDescription(dto.description);
        property.setPrice(dto.price);
        property.setAddress(dto.address);
        property.setCity(dto.city);
        property.setOperationType(Property.OperationType.valueOf(dto.operationType));
        property.setPropertyType(Property.PropertyType.valueOf(dto.propertyType));
        property.setBedrooms(dto.bedrooms);
        property.setBathrooms(dto.bathrooms);
        property.setAreaSqMeters(dto.areaSqMeters);
        property.setContactName(dto.contactName);
        property.setContactPhone(dto.contactPhone);
        property.setGoogleMapsUrl(dto.googleMapsUrl);
        property.setOwner(owner);
        Property saved = propertyRepository.save(property);

        // Guardar imágenes
        if (dto.imageUrls != null) {
            for (String url : dto.imageUrls) {
                PropertyImage img = new PropertyImage();
                img.setProperty(saved);
                img.setImageUrl(url);
                propertyImageRepository.save(img);
            }
        }

        // Guardar amenities
        if (dto.amenityIds != null) {
            for (Long amenityId : dto.amenityIds) {
                Amenity amenity = amenityRepository.findById(amenityId).orElse(null);
                if (amenity != null) {
                    PropertyAmenity pa = new PropertyAmenity(saved, amenity);
                    propertyAmenityRepository.save(pa);
                }
            }
        }
        return saved;
    }

    @Transactional
    public Property createProperty(PropertyCreateDTO dto) {
        User owner = null;
        if (dto.ownerId != null) {
            owner = userRepository.findById(dto.ownerId).orElse(null);
        }
        return createProperty(dto, owner);
    }

    public Optional<Property> findPropertyById(Long id) {
        return propertyRepository.findById(id);
    }


    public List<Property> searchProperties(SearchFiltersDTO filters) {
        // Implementación simple: solo filtra por ciudad, precio y habitaciones
        // Para búsquedas complejas, usar Specification o QueryDSL
        List<Property> all = propertyRepository.findAll();
        return all.stream()
            .filter(p -> filters.city == null || p.getCity().equalsIgnoreCase(filters.city))
            .filter(p -> filters.minPrice == null || p.getPrice().compareTo(filters.minPrice) >= 0)
            .filter(p -> filters.maxPrice == null || p.getPrice().compareTo(filters.maxPrice) <= 0)
            .filter(p -> filters.minBedrooms == null || (p.getBedrooms() != null && p.getBedrooms() >= filters.minBedrooms))
            .filter(p -> filters.maxBedrooms == null || (p.getBedrooms() != null && p.getBedrooms() <= filters.maxBedrooms))
            .filter(p -> filters.operationType == null || p.getOperationType().name().equalsIgnoreCase(filters.operationType))
            .filter(p -> filters.propertyType == null || p.getPropertyType().name().equalsIgnoreCase(filters.propertyType))
            .toList();
    }


    @Transactional
    public Property updateProperty(Long propertyId, PropertyUpdateDTO dto) {
        Property property = propertyRepository.findById(propertyId).orElse(null);
        if (property == null) return null;
        if (dto.title != null) property.setTitle(dto.title);
        if (dto.description != null) property.setDescription(dto.description);
        if (dto.price != null) property.setPrice(dto.price);
        if (dto.address != null) property.setAddress(dto.address);
        if (dto.city != null) property.setCity(dto.city);
        if (dto.operationType != null) property.setOperationType(Property.OperationType.valueOf(dto.operationType));
        if (dto.propertyType != null) property.setPropertyType(Property.PropertyType.valueOf(dto.propertyType));
        if (dto.bedrooms != null) property.setBedrooms(dto.bedrooms);
        if (dto.bathrooms != null) property.setBathrooms(dto.bathrooms);
        if (dto.areaSqMeters != null) property.setAreaSqMeters(dto.areaSqMeters);
        if (dto.contactName != null) property.setContactName(dto.contactName);
        if (dto.contactPhone != null) property.setContactPhone(dto.contactPhone);
        if (dto.googleMapsUrl != null) property.setGoogleMapsUrl(dto.googleMapsUrl);
        // Actualizar amenities e imágenes si es necesario (lógica adicional)
        return propertyRepository.save(property);
    }


    @Transactional
    public void addImageToProperty(Long propertyId, String imageUrl) {
        Property property = propertyRepository.findById(propertyId).orElse(null);
        if (property == null) return;
        PropertyImage img = new PropertyImage();
        img.setProperty(property);
        img.setImageUrl(imageUrl);
        propertyImageRepository.save(img);
    }


    @Transactional
    public void addAmenityToProperty(Long propertyId, Long amenityId) {
        Property property = propertyRepository.findById(propertyId).orElse(null);
        Amenity amenity = amenityRepository.findById(amenityId).orElse(null);
        if (property == null || amenity == null) return;
        PropertyAmenity pa = new PropertyAmenity(property, amenity);
        propertyAmenityRepository.save(pa);
    }
}
