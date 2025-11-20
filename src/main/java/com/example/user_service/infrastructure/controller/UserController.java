package com.example.user_service.infrastructure.controller;

import com.example.user_service.application.dto.CreateUserRequest;
import com.example.user_service.application.dto.UpdateUserRequest;
import com.example.user_service.application.dto.UserResponse;
import com.example.user_service.application.port.in.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final SearchUserByEmailUseCase searchUserByEmailUseCase;

     public UserController(CreateUserUseCase createUserUseCase,
                          GetUserUseCase getUserUseCase,
                          ListUsersUseCase listUsersUseCase,
                          UpdateUserUseCase updateUserUseCase,
                          DeleteUserUseCase deleteUserUseCase,
                          SearchUserByEmailUseCase searchUserByEmailUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.searchUserByEmailUseCase = searchUserByEmailUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest req) {
        UserResponse created = createUserUseCase.createUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<UserResponse> listAll() {
        return listUsersUseCase.listUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        return getUserUseCase.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest req) {
        return updateUserUseCase.updateUser(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUserUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public UserResponse findByEmail(@RequestParam String email) {
        return searchUserByEmailUseCase.getUserByEmail(email);
    }
}
