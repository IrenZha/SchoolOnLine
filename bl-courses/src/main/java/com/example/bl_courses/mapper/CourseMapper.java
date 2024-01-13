package com.example.bl_courses.mapper;

import com.example.bl_courses.domain.Category;
import com.example.bl_courses.domain.Course;
import com.example.bl_courses.dto.CategoryDto;
import com.example.bl_courses.dto.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "id", ignore = true)
    Course toEntity(CourseDto dto);

    CourseDto toDto(Course entity);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Course toUpdate, CourseDto dto);

    List<CourseDto> toDtos(List<Course> entities);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);

    List<CategoryDto> toCategoryDtos(List<Category> category);
}
