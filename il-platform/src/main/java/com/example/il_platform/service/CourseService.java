package com.example.il_platform.service;

import com.example.il_platform.dto.course.CategoryDto;
import com.example.il_platform.dto.course.Complexity;
import com.example.il_platform.dto.course.CourseDto;
import com.example.il_platform.dto.feedback.FeedbackDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    void save(CourseDto dto);

    CourseDto getById(UUID id);

    CourseDto update(UUID id, CourseDto dto);

    List<CourseDto> getAllCourses();

    List<CategoryDto> getAllCategories();

    CourseDto setAvatar(UUID id, MultipartFile file);

    List<CourseDto> getFreeCourses();

    List<CourseDto> search(String search);

    List<CourseDto> findAllByComplexity(Complexity complexity);

    List<CourseDto> findAllByCategory(UUID categoryId);

    Double countRatingByCourse(UUID courseId);

    List<CourseDto> getAllByAuthor(UUID authorId);

    List<FeedbackDto> getAllFeedbacksByCourse(UUID courseId);


}
