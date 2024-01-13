package com.example.il_platform.service;

import com.example.il_platform.dto.lesson.LessonDto;
import com.example.il_platform.dto.lesson.MaterialDto;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    LessonDto create(LessonDto dto);

    LessonDto getById(UUID id);

    List<LessonDto> getAll(UUID courseId);

    LessonDto update(UUID id, LessonDto dto);

}
