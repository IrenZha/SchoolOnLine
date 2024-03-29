package com.example.bllessons.repository;

import com.example.bllessons.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> findAllByCourseId(UUID courseId);
}
