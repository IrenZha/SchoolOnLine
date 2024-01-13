package com.example.bllessons.service;

import com.example.bllessons.dto.LessonDto;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    LessonDto create(LessonDto dto);
    LessonDto getById(UUID id);
    LessonDto update(UUID id,LessonDto dto);
    List<LessonDto> getAll(UUID courseId);
}