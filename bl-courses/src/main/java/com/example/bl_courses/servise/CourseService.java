package com.example.bl_courses.servise;

import com.example.bl_courses.domain.Complexity;
import com.example.bl_courses.dto.CategoryDto;
import com.example.bl_courses.dto.CourseDto;
import com.example.bl_courses.exc.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    void save(CourseDto dto);

    CourseDto getById(UUID id) throws EntityNotFoundException;

    void deleteCourse(UUID id);

    void update(UUID id, CourseDto dto);

    List<CourseDto> getAll();

    List<CourseDto> findAllByPriceIs();

    List<CourseDto> search(String search);

    List<CourseDto> findAllByComplexity(Complexity complexity);

    CategoryDto getCategoryById(UUID id);

    List<CategoryDto> getAllCategories();

    List<CourseDto> findByAuthorId(UUID authorId);

    List<CourseDto> findAllByCategory(UUID categoryId);
}
