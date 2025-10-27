package com.trainee.backend_project.controller;

import com.trainee.backend_project.dto.UserCreateDTO;
import com.trainee.backend_project.dto.UserUpdateDTO;
import com.trainee.backend_project.dto.UserResponseDTO;
import com.trainee.backend_project.model.User;
import com.trainee.backend_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO dto) {
        User u = userService.registerUser(dto);
        return ResponseEntity.ok(toDto(u));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list() {
        List<User> users = userService.findAllUsers();
        List<UserResponseDTO> res = users.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        return userService.findById(id)
            .map(u -> ResponseEntity.ok(toDto(u)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        User updated = userService.updateUser(id, dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean ok = userService.deleteUser(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    private UserResponseDTO toDto(User u) {
        UserResponseDTO r = new UserResponseDTO();
        r.id = u.getId();
        r.name = u.getName();
        r.email = u.getEmail();
        r.phoneNumber = u.getPhoneNumber();
        r.createdAt = u.getCreatedAt();
        r.updatedAt = u.getUpdatedAt();
        return r;
    }
}
