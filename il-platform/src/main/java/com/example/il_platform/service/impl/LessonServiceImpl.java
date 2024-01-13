package com.example.il_platform.service.impl;

import com.example.il_platform.client.LessonClient;
import com.example.il_platform.dto.lesson.LessonDto;
import com.example.il_platform.dto.lesson.MaterialDto;
import com.example.il_platform.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonClient client;
    @Override
    public LessonDto create(LessonDto dto) {
        return client.create(dto);
    }

    @Override
    public LessonDto getById(UUID id) {
        return client.getById(id);
    }

    @Override
    public List<LessonDto> getAll(UUID courseId) {
        return client.getAll(courseId);
    }

    @Override
    public LessonDto update(UUID id, LessonDto dto) {
        return client.update(id, dto);
    }

}
