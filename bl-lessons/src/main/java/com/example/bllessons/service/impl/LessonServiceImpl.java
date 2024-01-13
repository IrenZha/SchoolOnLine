package com.example.bllessons.service.impl;

import com.example.bllessons.domain.Lesson;
import com.example.bllessons.dto.LessonDto;
import com.example.bllessons.exc.EntityNotFoundException;
import com.example.bllessons.mapper.LessonMapper;
import com.example.bllessons.repository.LessonRepository;
import com.example.bllessons.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Override
    public LessonDto create(LessonDto dto) {
        Lesson lesson = lessonRepository.save(lessonMapper.toEntity(dto));
        return lessonMapper.toDto(lesson);
    }

    @Override
    public LessonDto getById(UUID id) {
        Optional<Lesson> byId = lessonRepository.findById(id);
        if (byId.isPresent()) {
            Lesson lesson =byId.get();
            return lessonMapper.toDto(lesson);
        }else throw new EntityNotFoundException(id.toString());
    }

    @Override
    public LessonDto update(UUID id, LessonDto dto) {
        Optional<Lesson> byId = lessonRepository.findById(id);
        if (byId.isPresent()) {
            Lesson lesson =byId.get();
            lessonMapper.update(lesson, dto);
            return lessonMapper.toDto(lesson);
        }else throw new EntityNotFoundException(id.toString());
    }

    @Override
    public List<LessonDto> getAll(UUID courseId) {
        return lessonMapper.toDtos(lessonRepository.findAllByCourseId(courseId));
    }
}
