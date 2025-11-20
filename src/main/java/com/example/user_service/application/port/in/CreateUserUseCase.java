package com.example.user_service.application.port.in;

import com.example.user_service.application.dto.CreateUserRequest;
import com.example.user_service.application.dto.UserResponse;

public interface CreateUserUseCase {
    UserResponse createUser(CreateUserRequest request);
}