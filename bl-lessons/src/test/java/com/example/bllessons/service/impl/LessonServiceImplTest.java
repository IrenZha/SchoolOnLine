package com.example.bllessons.service.impl;

import com.example.bllessons.domain.Lesson;
import com.example.bllessons.dto.LessonDto;
import com.example.bllessons.exc.EntityNotFoundException;
import com.example.bllessons.mapper.LessonMapperImpl;
import com.example.bllessons.repository.LessonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {
    @Mock
    private LessonRepository repository;
    @Mock
    private LessonMapperImpl mapper;
    @InjectMocks
    private LessonServiceImpl lessonService;

    @Test
    public void create() {
        Lesson lesson = new Lesson(UUID.randomUUID(), "theme", "text", null);
        LessonDto lessonDto = new LessonDto();
        Mockito.when(mapper.toEntity(lessonDto)).thenReturn(lesson);
        Mockito.when(mapper.toDto(lesson)).thenCallRealMethod();
        Mockito.when(repository.save(lesson)).thenReturn(lesson);

        LessonDto result = lessonService.create(lessonDto);

        Assertions.assertEquals("theme", result.getTheme());
        Assertions.assertEquals("text", result.getText());

    }

    @Test
    public void getByIdExc() {
        UUID id = UUID.randomUUID();

        Assertions.assertThrows(EntityNotFoundException.class, () -> lessonService.getById(id));
    }

    @Test
    public void getById() {
        UUID id = UUID.randomUUID();
        Lesson lesson = new Lesson(id, "theme", "text", null);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(lesson));
        Mockito.when(mapper.toDto(lesson)).thenCallRealMethod();

        LessonDto result = lessonService.getById(id);

        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals("theme", result.getTheme());
        Assertions.assertEquals("text", result.getText());
    }

    @Test
    public void update() {
        UUID id = UUID.randomUUID();
        Lesson lesson = new Lesson(id, "theme", "text", null);
        LessonDto lessonDto = new LessonDto(null, "theme1", "text1", null);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(lesson));
        Mockito.doCallRealMethod().when(mapper).update(lesson, lessonDto);
        Mockito.when(mapper.toDto(lesson)).thenCallRealMethod();


        LessonDto result = lessonService.update(id, lessonDto);


        Assertions.assertEquals("theme1", result.getTheme());
        Assertions.assertEquals("text1", result.getText());

    }
    @Test
    public void updateExc() {
        UUID id = UUID.randomUUID();
        LessonDto lessonDto = new LessonDto();

        Assertions.assertThrows(EntityNotFoundException.class, () -> lessonService.update(id,lessonDto));
    }
    @Test
    public void getAllByCourseId() {
        Lesson lesson1 = new Lesson();
        Lesson lesson2 = new Lesson();
        List<Lesson> lessons = Arrays.asList(lesson1, lesson2);
        Mockito.when(repository.findAllByCourseId(Mockito.any())).thenReturn(lessons);
        Mockito.when(mapper.toDtos(lessons)).thenCallRealMethod();

        List<LessonDto> result = lessonService.getAll(Mockito.any());


        Assertions.assertEquals(2, result.size());
    }
}