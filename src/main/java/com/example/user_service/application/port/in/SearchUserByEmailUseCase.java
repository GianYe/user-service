package com.example.user_service.application.port.in;

import com.example.user_service.application.dto.UserResponse;

public interface SearchUserByEmailUseCase {
    UserResponse getUserByEmail(String email);
}