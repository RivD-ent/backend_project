package com.trainee.backend_project.controller;

import com.trainee.backend_project.dto.FavoriteCreateDTO;
import com.trainee.backend_project.dto.PropertyListDTO;
import com.trainee.backend_project.dto.PropertyResponseDTO;
import com.trainee.backend_project.mapper.PropertyMapper;
import com.trainee.backend_project.model.Property;
import com.trainee.backend_project.service.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<PropertyResponseDTO> addFavorite(@Valid @RequestBody FavoriteCreateDTO dto, Principal principal) {
        Property property = favoriteService.addFavorite(principal, dto.propertyId);
        return ResponseEntity.ok(PropertyMapper.toResponseDto(property));
    }

    @GetMapping
    public ResponseEntity<List<PropertyListDTO>> getFavorites(Principal principal) {
        List<Property> favorites = favoriteService.getFavorites(principal);
        List<PropertyListDTO> response = favorites.stream()
            .map(PropertyMapper::toListDto)
            .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long propertyId, Principal principal) {
        favoriteService.removeFavorite(principal, propertyId);
        return ResponseEntity.noContent().build();
    }
}
