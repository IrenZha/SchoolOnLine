package com.example.il_platform.service.impl;

import com.example.il_platform.client.LessonClient;
import com.example.il_platform.dto.lesson.LessonDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {
    @Mock
    private LessonClient client;
    @InjectMocks
    private LessonServiceImpl service;

    @Test
    public void create() {
        UUID id = UUID.randomUUID();
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(id);
        Mockito.when(client.create(lessonDto)).thenReturn(lessonDto);

        LessonDto result = service.create(lessonDto);

        Assertions.assertEquals(id, result.getId());
    }

    @Test
    public void getById() {
        UUID id = UUID.randomUUID();
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(id);
        Mockito.when(client.getById(id)).thenReturn(lessonDto);

        LessonDto result = service.getById(id);

        Assertions.assertEquals(id, result.getId());
    }

    @Test
    public void getAll() {
        UUID courseId = UUID.randomUUID();
        LessonDto lessonDto = new LessonDto();
        List<LessonDto> lessons = List.of(lessonDto);
        Mockito.when(client.getAll(courseId)).thenReturn(lessons);

        List<LessonDto> result = service.getAll(courseId);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(lessonDto, result.get(0));
    }

    @Test
    public void update() {
        UUID id = UUID.randomUUID();
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(id);
        Mockito.when(client.update(id, lessonDto)).thenReturn(lessonDto);

        LessonDto result = service.update(id, lessonDto);

        Assertions.assertEquals(id, result.getId());
    }
}