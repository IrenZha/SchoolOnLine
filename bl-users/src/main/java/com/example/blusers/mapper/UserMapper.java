package com.example.blusers.mapper;

import com.example.blusers.domain.User;
import com.example.blusers.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto dto);

    UserDto toDto(User user);

    List<UserDto> toDtos(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void update(@MappingTarget User toUpdate, UserDto dto);

}

