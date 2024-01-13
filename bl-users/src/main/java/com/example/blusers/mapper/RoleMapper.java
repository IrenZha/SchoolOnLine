package com.example.blusers.mapper;

import com.example.blusers.domain.Role;
import com.example.blusers.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Role toRoleEntity(RoleDto roleDto);

    @Mapping(target = "name", source = "name")
    RoleDto toDto(Role role);

    List<RoleDto> toRoleDtos(List<Role> roles);

}
