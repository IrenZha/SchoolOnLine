package com.example.bl_avatars.mapper;

import com.example.bl_avatars.domain.Avatar;
import com.example.bl_avatars.dto.AvatarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvatarMapper {
    @Mapping(target = "fileId", ignore = true)
    Avatar toEntity(AvatarDto dto);
    AvatarDto toDto(Avatar avatar);
    List<AvatarDto> toDtos(List<Avatar> avatars);
}
