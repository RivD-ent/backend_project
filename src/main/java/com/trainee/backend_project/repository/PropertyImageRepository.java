package com.trainee.backend_project.repository;

import com.trainee.backend_project.model.Property;
import com.trainee.backend_project.model.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
	Optional<PropertyImage> findByImageUrl(String imageUrl);
    void deleteByProperty(Property property);
}
