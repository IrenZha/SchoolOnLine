package com.example.bl_courses.repository;

import com.example.bl_courses.domain.Category;
import com.example.bl_courses.domain.Complexity;
import com.example.bl_courses.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
  List<Course> findAllByAuthorId(UUID authorId);
  List<Course> findAllByPrice(Double price);
  List<Course> findAllByTitleContainsOrDescriptionContains(String title, String description);
  List<Course> findAllByComplexity(Complexity complexity);
  List<Course> findAllByCategory(Category category);
  }
