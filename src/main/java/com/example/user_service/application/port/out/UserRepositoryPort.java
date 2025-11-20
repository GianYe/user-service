package com.example.user_service.application.port.out;

import com.example.user_service.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(UUID id);
    void deleteById(UUID id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
