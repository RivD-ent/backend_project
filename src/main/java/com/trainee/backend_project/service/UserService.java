package com.trainee.backend_project.service;

import com.trainee.backend_project.model.User;
import com.trainee.backend_project.repository.UserRepository;
import com.trainee.backend_project.dto.UserCreateDTO;
import com.trainee.backend_project.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(UserCreateDTO dto) {
        // Simple validation
        if (dto == null || dto.email == null || dto.name == null || dto.password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name, email and password are required");
        }
        if (userRepository.findByEmail(dto.email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email already registered");
        }
        User u = new User();
        u.setName(dto.name);
        u.setEmail(dto.email);
        // NOTE: password should be hashed in a real app. For now store raw (not recommended).
    u.setPassword(passwordEncoder.encode(dto.password));
        u.setPhoneNumber(dto.phoneNumber);
        return userRepository.save(u);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User updateUser(Long id, UserUpdateDTO dto) {
        Optional<User> o = userRepository.findById(id);
        if (o.isEmpty()) return null;
        User u = o.get();
        if (dto.name != null) u.setName(dto.name);
    if (dto.password != null) u.setPassword(passwordEncoder.encode(dto.password));
        if (dto.phoneNumber != null) u.setPhoneNumber(dto.phoneNumber);
        return userRepository.save(u);
    }

    @Transactional
    public boolean deleteUser(Long id) {
        Optional<User> o = userRepository.findById(id);
        if (o.isEmpty()) return false;
        userRepository.delete(o.get());
        return true;
    }

    public Object getPropertiesListedByUser(Integer userId) {
        // Left as previously: could implement later
        return null;
    }
}
