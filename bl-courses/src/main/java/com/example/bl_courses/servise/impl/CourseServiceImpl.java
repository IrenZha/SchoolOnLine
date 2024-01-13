package com.example.bl_courses.servise.impl;

import com.example.bl_courses.domain.Category;
import com.example.bl_courses.domain.Complexity;
import com.example.bl_courses.domain.Course;
import com.example.bl_courses.dto.CategoryDto;
import com.example.bl_courses.dto.CourseDto;
import com.example.bl_courses.exc.EntityNotFoundException;
import com.example.bl_courses.mapper.CourseMapper;
import com.example.bl_courses.repository.CourseRepository;
import com.example.bl_courses.repository.CategoryRepository;
import com.example.bl_courses.servise.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final CourseMapper courseMapper;


    @Override
    public void save(CourseDto course) {
        courseRepository.save(courseMapper.toEntity(course));
    }

    @Override
    public CourseDto getById(UUID id) {
        Optional<Course> byId = courseRepository.findById(id);
        if (byId.isPresent()) {
            Course course = byId.get();
            return courseMapper.toDto(course);
        } else throw new EntityNotFoundException(id.toString());
    }

    @Override
    public void deleteCourse(UUID id) {
        Optional<Course> byId = courseRepository.findById(id);
        if (byId.isPresent()) {
            courseRepository.deleteById(id);
        } else throw new EntityNotFoundException(id.toString());
    }

    @Override
    public void update(UUID id, CourseDto dto) {
        Optional<Course> byId = courseRepository.findById(id);
        if (byId.isPresent()) {
            Course course = byId.get();
            courseMapper.update(course, dto);
        } else throw new EntityNotFoundException(id.toString());
    }

    @Override
    public List<CourseDto> getAll() {
        return courseMapper.toDtos(courseRepository.findAll());
    }

    @Override
    public List<CourseDto> findAllByPriceIs() {
        Double price = 0.0;
        List<Course> byPrice = courseRepository.findAllByPrice(price);
        return courseMapper.toDtos(byPrice);
    }

    @Override
    public List<CourseDto> search(String search) {
        if (search == null || search.isBlank()) {
            return getAll();
        }
        return courseMapper.toDtos(courseRepository.findAllByTitleContainsOrDescriptionContains(search, search));
    }

    @Override
    public List<CourseDto> findAllByComplexity(Complexity complexity) {
        return courseMapper.toDtos(courseRepository.findAllByComplexity(complexity));
    }

    @Override
    public CategoryDto getCategoryById(UUID id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            Category category = byId.get();
            return courseMapper.toCategoryDto(category);
        } else throw new EntityNotFoundException(id.toString());
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return courseMapper.toCategoryDtos(categoryRepository.findAll());
    }


    @Override
    public List<CourseDto> findByAuthorId(UUID authorId) {
        return courseMapper.toDtos(courseRepository.findAllByAuthorId(authorId));
    }

    @Override
    public List<CourseDto> findAllByCategory(UUID categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()){
            return courseMapper.toDtos(courseRepository.findAllByCategory(category.get()));
        }
       else throw new EntityNotFoundException(categoryId.toString());
    }
}
