package com.trainee.backend_project.repository;

import com.trainee.backend_project.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
	Optional<Amenity> findByName(String name);
}
