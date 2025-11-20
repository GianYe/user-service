package com.example.user_service.infrastructure.persistence;

import com.example.user_service.application.port.out.UserRepositoryPort;
import com.example.user_service.domain.model.User;
import com.example.user_service.infrastructure.mapper.UserEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaUserRepositoryAdapter implements UserRepositoryPort{
    
    private final PostgreUserRepository repo;
    private final UserEntityMapper mapper;

    public JpaUserRepositoryAdapter(PostgreUserRepository repo, UserEntityMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity saved = repo.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<User> findAll() {
        return mapper.toDomainList(repo.findAll());
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }
}
