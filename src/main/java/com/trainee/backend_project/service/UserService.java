package com.trainee.backend_project.service;

import com.trainee.backend_project.model.User;
import com.trainee.backend_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    // Si usas Spring Security, puedes inyectar un PasswordEncoder
    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(/*UserRegistrationDTO dto*/) {
        // Implementar lógica de registro y encriptación de contraseña
        return null;
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User updateUserProfile(Integer userId/*, UserProfileDTO dto*/) {
        // Implementar lógica de actualización de perfil
        return null;
    }

    public Object getPropertiesListedByUser(Integer userId) {
        // Implementar lógica para obtener propiedades de un usuario
        return null;
    }
}
