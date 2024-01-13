package com.example.blreviews.repository;

import com.example.blreviews.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    List<Feedback> findAllByCourseId(UUID courseId);
    List<Feedback> findAllByUserId(UUID userId);
    Integer countAllByCourseIdAndRatingGreaterThan(UUID courseId, Integer rating);
}
