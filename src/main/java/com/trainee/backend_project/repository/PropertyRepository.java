package com.trainee.backend_project.repository;

import com.trainee.backend_project.model.Property;
import com.trainee.backend_project.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
	Optional<Property> findByTitle(String title);
    List<Property> findAllByOwner(User owner);
}
