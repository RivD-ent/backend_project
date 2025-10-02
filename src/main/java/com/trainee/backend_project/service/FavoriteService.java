package com.trainee.backend_project.service;

import com.trainee.backend_project.model.Favorite;
import com.trainee.backend_project.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Transactional
    public Favorite addFavorite(Integer userId, Integer propertyId) {
        // Implementar lógica para añadir favorito
        return null;
    }

    @Transactional
    public void removeFavorite(Integer userId, Integer propertyId) {
        // Implementar lógica para eliminar favorito
    }

    public List<Favorite> getFavoritePropertiesForUser(Integer userId) {
        // Implementar lógica para obtener favoritos
        return null;
    }
}
