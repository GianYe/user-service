package com.example.user_service.application.port.in;

import com.example.user_service.application.dto.UserResponse;
import java.util.List;

public interface ListUsersUseCase {
    List<UserResponse> listUsers();
}