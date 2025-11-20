package com.example.user_service.application.service;

import com.example.user_service.application.dto.CreateUserRequest;
import com.example.user_service.application.dto.UpdateUserRequest;
import com.example.user_service.application.dto.UserResponse;
import com.example.user_service.application.port.out.UserRepositoryPort;
import com.example.user_service.domain.model.Status;
import com.example.user_service.domain.model.User;
import com.example.user_service.infrastructure.mapper.UserDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepositoryPort repo;
    private UserDtoMapper dtoMapper;
    private UserService service;

    @BeforeEach
    void setup() {
        repo = mock(UserRepositoryPort.class);
        dtoMapper = mock(UserDtoMapper.class);
        service = new UserService(repo, dtoMapper);
    }

    @Test
    void createUser_success() {
        CreateUserRequest req = new CreateUserRequest();
        req.setFirstName("Gia");
        req.setLastName("Esp");
        req.setEmail("gia@example.com");

        User domain = new User();
        domain.setFirstName("Gia");
        domain.setLastName("Esp");
        domain.setEmail("gia@example.com");

        User saved = new User();
        saved.setId(UUID.randomUUID());
        saved.setFirstName("Gia");
        saved.setLastName("Esp");
        saved.setEmail("gia@example.com");
        saved.setStatus(Status.ACTIVE);
        saved.setCreatedAt(OffsetDateTime.now());
        saved.setUpdatedAt(OffsetDateTime.now());

        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setFirstName(saved.getFirstName());
        response.setLastName(saved.getLastName());
        response.setEmail(saved.getEmail());
        response.setStatus(saved.getStatus().name());
        response.setCreatedAt(saved.getCreatedAt());
        response.setUpdatedAt(saved.getUpdatedAt());

        when(repo.existsByEmail(req.getEmail())).thenReturn(false);
        when(dtoMapper.toDomain(req)).thenReturn(domain);
        when(repo.save(ArgumentMatchers.any(User.class))).thenReturn(saved);
        when(dtoMapper.toResponse(saved)).thenReturn(response);

        UserResponse out = service.createUser(req);

        assertNotNull(out);
        assertEquals("Gia", out.getFirstName());
        assertEquals("gia@example.com", out.getEmail());
        verify(repo, times(1)).save(any(User.class));
    }

    @Test
    void createUser_emailAlreadyExists_throwsException() {
        CreateUserRequest req = new CreateUserRequest();
        req.setEmail("exists@example.com");

        when(repo.existsByEmail(req.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.createUser(req));
        verify(repo, never()).save(any());
    }

    @Test
    void getUserById_notFound_throwsException() {
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserService.ResourceNotFoundException.class,
                () -> service.getUserById(id));
    }

    @Test
    void updateUser_success() {
        UUID id = UUID.randomUUID();

        User existing = new User();
        existing.setId(id);
        existing.setFirstName("Old");
        existing.setLastName("Name");
        existing.setEmail("old@example.com");
        existing.setStatus(Status.ACTIVE);
        existing.setCreatedAt(OffsetDateTime.now().minusDays(1));
        existing.setUpdatedAt(OffsetDateTime.now().minusDays(1));

        UpdateUserRequest req = new UpdateUserRequest();
        req.setFirstName("New");
        req.setLastName("Name");
        req.setEmail("old@example.com");

        User saved = new User();
        saved.setId(id);
        saved.setFirstName("New");
        saved.setLastName("Name");
        saved.setEmail("old@example.com");
        saved.setStatus(Status.ACTIVE);
        saved.setCreatedAt(existing.getCreatedAt());
        saved.setUpdatedAt(OffsetDateTime.now());

        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setFirstName(saved.getFirstName());
        response.setLastName(saved.getLastName());
        response.setEmail(saved.getEmail());
        response.setStatus(saved.getStatus().name());
        response.setCreatedAt(saved.getCreatedAt());
        response.setUpdatedAt(saved.getUpdatedAt());

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.existsByEmail(req.getEmail())).thenReturn(false);
        // dtoMapper.updateDomainFromRequest se usa con void, lo verificamos por interacción
        doAnswer(invocation -> {
            UpdateUserRequest r = invocation.getArgument(0);
            User u = invocation.getArgument(1);
            u.setFirstName(r.getFirstName());
            u.setLastName(r.getLastName());
            u.setEmail(r.getEmail());
            return null;
        }).when(dtoMapper).updateDomainFromRequest(eq(req), any(User.class));
        when(repo.save(any(User.class))).thenReturn(saved);
        when(dtoMapper.toResponse(saved)).thenReturn(response);

        UserResponse out = service.updateUser(id, req);

        assertEquals("New", out.getFirstName());
        verify(repo, times(1)).save(any(User.class));
    }
}
