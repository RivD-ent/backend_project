package com.trainee.backend_project.repository;

import com.trainee.backend_project.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
	Optional<Property> findByTitle(String title);
}
