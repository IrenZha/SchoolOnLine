package com.example.blreviews.service;

import com.example.blreviews.dto.FeedbackDto;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {
    FeedbackDto save(FeedbackDto feedbackDto);
    FeedbackDto getById(UUID id);
    FeedbackDto update(UUID id, FeedbackDto feedbackDto);
    List<FeedbackDto> getAll();
    List<FeedbackDto> getByCourseId(UUID courseId);
    List<FeedbackDto> getByUserId(UUID userId);
    Double countRatingByCourse(UUID courseId);
}
