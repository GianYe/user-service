package com.example.user_service.infrastructure.mapper;

import com.example.user_service.domain.model.User;
import com.example.user_service.infrastructure.persistence.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity toEntity(User domain);

    User toDomain(UserEntity entity);

    List<User> toDomainList(List<UserEntity> entities);
}
