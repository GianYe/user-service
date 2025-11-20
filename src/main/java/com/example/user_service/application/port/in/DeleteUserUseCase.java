package com.example.user_service.application.port.in;

import java.util.UUID;

public interface DeleteUserUseCase {
    void deleteUser(UUID id);
}