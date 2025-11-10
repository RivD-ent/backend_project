package com.trainee.backend_project.service;

import com.trainee.backend_project.model.Favorite;
import com.trainee.backend_project.model.FavoriteId;
import com.trainee.backend_project.model.Property;
import com.trainee.backend_project.model.User;
import com.trainee.backend_project.repository.FavoriteRepository;
import com.trainee.backend_project.repository.PropertyRepository;
import com.trainee.backend_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Transactional
    public Property addFavorite(Principal principal, Long propertyId) {
        User user = resolveUser(principal);
        Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "propiedad no encontrada"));

        if (favoriteRepository.existsByUserAndProperty(user, property)) {
            return property;
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProperty(property);
        favoriteRepository.save(favorite);
        return property;
    }

    @Transactional(readOnly = true)
    public List<Property> getFavorites(Principal principal) {
        User user = resolveUser(principal);
        return favoriteRepository.findAllByUser(user).stream()
            .map(Favorite::getProperty)
            .toList();
    }

    @Transactional
    public void removeFavorite(Principal principal, Long propertyId) {
        User user = resolveUser(principal);
        Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "propiedad no encontrada"));

        FavoriteId id = new FavoriteId(user.getId(), propertyId);
        if (!favoriteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "favorito no encontrado");
        }
        favoriteRepository.deleteByUserAndProperty(user, property);
    }

    private User resolveUser(Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "usuario no autenticado");
        }
        return userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "usuario no encontrado"));
    }
}
