package com.example.bl_courses.web;

import com.example.bl_courses.domain.Category;
import com.example.bl_courses.domain.Complexity;
import com.example.bl_courses.dto.CategoryDto;
import com.example.bl_courses.dto.CourseDto;
import com.example.bl_courses.exc.EntityNotFoundException;
import com.example.bl_courses.servise.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService service;

    @PostMapping
    public void save(@RequestBody CourseDto course) {
        service.save(course);
    }

    @GetMapping("/{id}")
    public CourseDto getById(@PathVariable(name = "id") UUID id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable(name = "id") UUID id) {
        service.deleteCourse(id);
    }

    @PutMapping("/update/{id}")
    public CourseDto update(@PathVariable(name = "id") UUID id, @RequestBody CourseDto dto) {
        service.update(id, dto);
        return dto;
    }

    @GetMapping
    public List<CourseDto> getAllCourses() {
        return service.getAll();
    }

    @GetMapping("/author/{authorId}")
    public List<CourseDto> findByAuthorId(@PathVariable UUID authorId) {
        return service.findByAuthorId(authorId);
    }

    @GetMapping("/free")
    public List<CourseDto> getFreeCourses() {
        return service.findAllByPriceIs();
    }

    @GetMapping("/search")
    public List<CourseDto> search(@RequestParam(name = "search") String search) {
        return service.search(search);
    }

    @GetMapping("/complexity")
    public List<CourseDto> findAllByComplexity(@RequestParam("complexity") Complexity complexity) {
        return service.findAllByComplexity(complexity);
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        return service.getAllCategories();
    }

    @GetMapping("/category/{id}")
    public CategoryDto getCategoryById(@PathVariable(name = "id") UUID id) {
        return service.getCategoryById(id);
    }

    @GetMapping("/category")
    public List<CourseDto> findAllByCategory(@RequestParam(name = "categoryId") UUID categoryId) {
        return service.findAllByCategory(categoryId);
    }
}
