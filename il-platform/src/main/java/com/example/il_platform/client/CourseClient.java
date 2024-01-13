package com.example.il_platform.client;

import com.example.il_platform.dto.course.CategoryDto;
import com.example.il_platform.dto.course.Complexity;
import com.example.il_platform.dto.course.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "course-client", url = "${client.courses}")
public interface CourseClient {

    @PostMapping
    void save(@RequestBody CourseDto dto);

    @GetMapping("/{id}")
    CourseDto getById(@PathVariable(name = "id") UUID id);

    @DeleteMapping("/{id}")
    void deleteCourse(@PathVariable(name = "id") UUID id);

    @PutMapping("/update/{id}")
    CourseDto update(@PathVariable(name = "id") UUID id, @RequestBody CourseDto dto);

    @GetMapping("/author/{authorId}")
    List<CourseDto> findByAuthorId(@PathVariable UUID authorId);

    @GetMapping
    List<CourseDto> getAllCourses();

    @GetMapping("/free")
    List<CourseDto> getFreeCourses();

    @GetMapping("/search")
    List<CourseDto> search(@RequestParam(name = "search") String search);

    @GetMapping("/complexity")
    List<CourseDto> findAllByComplexity(@RequestParam("complexity") Complexity complexity);

    @GetMapping("/category/{id}")
    CategoryDto getCategoryById(@PathVariable(name = "id") UUID id);

    @GetMapping("/categories")
    List<CategoryDto> getAllCategories();

    @GetMapping("/category")
    List<CourseDto> findAllByCategory(@RequestParam(name = "categoryId") UUID categoryId);
}
