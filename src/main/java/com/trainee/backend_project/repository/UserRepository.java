package com.trainee.backend_project.repository;

import com.trainee.backend_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Puedes agregar métodos personalizados aquí
    Optional<User> findByEmail(String email);
}
