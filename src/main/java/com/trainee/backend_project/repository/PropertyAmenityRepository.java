package com.trainee.backend_project.repository;

import com.trainee.backend_project.model.PropertyAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyAmenityRepository extends JpaRepository<PropertyAmenity, Integer> {
}
