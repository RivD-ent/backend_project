package com.trainee.backend_project.repository;

import com.trainee.backend_project.model.Favorite;
import com.trainee.backend_project.model.FavoriteId;
import com.trainee.backend_project.model.Property;
import com.trainee.backend_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
	List<Favorite> findAllByUser(User user);
	Optional<Favorite> findByUserAndProperty(User user, Property property);
	boolean existsByUserAndProperty(User user, Property property);
	void deleteByUserAndProperty(User user, Property property);
}
