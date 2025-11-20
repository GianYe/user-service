package com.example.user_service.application.service;

import com.example.user_service.application.dto.CreateUserRequest;
import com.example.user_service.application.dto.UpdateUserRequest;
import com.example.user_service.application.dto.UserResponse;
import com.example.user_service.application.port.in.*;
import com.example.user_service.application.port.out.UserRepositoryPort;
import com.example.user_service.domain.model.Status;
import com.example.user_service.domain.model.User;
import com.example.user_service.infrastructure.mapper.UserDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService implements 
    CreateUserUseCase,
    GetUserUseCase,
    ListUsersUseCase,
    UpdateUserUseCase,
    DeleteUserUseCase,
    SearchUserByEmailUseCase{

    private final UserRepositoryPort repo;
    private final UserDtoMapper dtoMapper;


    public UserService(UserRepositoryPort repo, UserDtoMapper dtoMapper) {
        this.repo = repo;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (repo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = dtoMapper.toDomain(request);
        user.setId(UUID.randomUUID());
        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        
        User saved = repo.save(user);
        return dtoMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return dtoMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> listUsers() {
        return dtoMapper.toResponseList(repo.findAll());
    }

    @Override
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!existing.getEmail().equals(request.getEmail())
                && repo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        dtoMapper.updateDomainFromRequest(request, existing);
        existing.setUpdatedAt(OffsetDateTime.now());

        User saved = repo.save(existing);
        return dtoMapper.toResponse(saved);
    }

    @Override
    public void deleteUser(UUID id) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        repo.deleteById(existing.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return dtoMapper.toResponse(user);
    }

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String msg) {
            super(msg);
        }
    }
}
