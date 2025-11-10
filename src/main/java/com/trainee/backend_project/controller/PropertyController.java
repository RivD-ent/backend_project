package com.trainee.backend_project.controller;

import com.trainee.backend_project.dto.PropertyCreateDTO;
import com.trainee.backend_project.dto.PropertyListDTO;
import com.trainee.backend_project.dto.PropertyResponseDTO;
import com.trainee.backend_project.dto.PropertyUpdateDTO;
import com.trainee.backend_project.mapper.PropertyMapper;
import com.trainee.backend_project.model.Property;
import com.trainee.backend_project.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyResponseDTO> create(@Valid @RequestBody PropertyCreateDTO dto, Principal principal) {
        Property property = propertyService.createProperty(dto, principal);
        PropertyResponseDTO res = PropertyMapper.toResponseDto(property);
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<List<PropertyListDTO>> getAll() {
        List<Property> props = propertyService.findAllProperties();
        List<PropertyListDTO> list = props.stream()
            .map(PropertyMapper::toListDto)
            .toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PropertyUpdateDTO dto, Principal principal) {
        Property updated = propertyService.updateProperty(id, dto, principal);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(PropertyMapper.toResponseDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean ok = propertyService.deleteProperty(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDTO> getById(@PathVariable Long id) {
        Optional<Property> p = propertyService.findPropertyById(id);
        if (p.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(PropertyMapper.toResponseDto(p.get()));
    }
}
