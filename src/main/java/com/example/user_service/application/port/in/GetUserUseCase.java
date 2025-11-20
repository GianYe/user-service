package com.example.user_service.application.port.in;

import com.example.user_service.application.dto.UserResponse;

import java.util.UUID;

public interface GetUserUseCase {
    UserResponse getUserById(UUID id);
}