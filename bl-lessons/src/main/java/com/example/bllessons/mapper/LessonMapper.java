package com.example.bllessons.mapper;

import com.example.bllessons.domain.Lesson;
import com.example.bllessons.dto.LessonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonDto toDto(Lesson lesson);

    @Mapping(target = "id", ignore = true)
    Lesson toEntity(LessonDto dto);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Lesson toUpdate, LessonDto lessonDto);

    List<LessonDto> toDtos(List<Lesson> lessons);

}
