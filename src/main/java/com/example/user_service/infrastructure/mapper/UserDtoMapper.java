package com.example.user_service.infrastructure.mapper;

import com.example.user_service.application.dto.CreateUserRequest;
import com.example.user_service.application.dto.UpdateUserRequest;
import com.example.user_service.application.dto.UserResponse;
import com.example.user_service.domain.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    User toDomain(CreateUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDomainFromRequest(UpdateUserRequest request, @MappingTarget User user);

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
}