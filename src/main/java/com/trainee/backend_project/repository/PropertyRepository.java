package com.trainee.backend_project.repository;

import com.trainee.backend_project.model.Property;
import com.trainee.backend_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
	Optional<Property> findByTitle(String title);
    List<Property> findAllByOwner(User owner);
}
