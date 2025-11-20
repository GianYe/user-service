package com.example.user_service.application.port.in;

import com.example.user_service.application.dto.UpdateUserRequest;
import com.example.user_service.application.dto.UserResponse;

import java.util.UUID;

public interface UpdateUserUseCase {
    UserResponse updateUser(UUID id, UpdateUserRequest request);
}