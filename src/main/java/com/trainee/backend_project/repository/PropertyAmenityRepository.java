package com.trainee.backend_project.repository;

import com.trainee.backend_project.model.PropertyAmenity;
import com.trainee.backend_project.model.PropertyAmenityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyAmenityRepository extends JpaRepository<PropertyAmenity, PropertyAmenityId> {
}
