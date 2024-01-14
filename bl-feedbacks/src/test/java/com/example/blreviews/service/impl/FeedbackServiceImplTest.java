package com.example.blreviews.service.impl;

import com.example.blreviews.domain.Feedback;
import com.example.blreviews.dto.FeedbackDto;
import com.example.blreviews.exc.EntityNotFoundException;
import com.example.blreviews.mapper.FeedbackMapperImpl;
import com.example.blreviews.repository.FeedbackRepository;
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
class FeedbackServiceImplTest {
    @Mock
    private FeedbackRepository repository;

    @Mock
    private FeedbackMapperImpl mapper;

    @InjectMocks
    private FeedbackServiceImpl service;

    @Test
    public void save() {
        UUID id = UUID.randomUUID();
        Feedback feedback = new Feedback(id, null, null, 5, "text");
        FeedbackDto feedbackDto = new FeedbackDto();
        Mockito.when(mapper.toEntity(feedbackDto)).thenReturn(feedback);
        Mockito.when(repository.save(feedback)).thenReturn(feedback);
        Mockito.when(mapper.toDto(feedback)).thenCallRealMethod();

        FeedbackDto result = service.save(feedbackDto);

        Mockito.verify(repository, Mockito.times(1)).save(feedback);
        Assertions.assertEquals("text", result.getText());

    }

    @Test
    void getById() {
        UUID id = UUID.randomUUID();
        Feedback feedback = new Feedback(id, null, null, 5, "text");
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(feedback));
        Mockito.when(mapper.toDto(feedback)).thenCallRealMethod();

        FeedbackDto result = service.getById(id);

        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals("text", result.getText());
        Assertions.assertEquals(5, result.getRating());
    }

    @Test
    void getByIdExc() {
        UUID id = UUID.randomUUID();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void update() {
        UUID id = UUID.randomUUID();
        Feedback feedback = new Feedback(id, null, null, 5, "text");
        FeedbackDto feedbackDto = new FeedbackDto(null, null, null, 3, "text1");
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(feedback));
        Mockito.doCallRealMethod().when(mapper).update(feedback, feedbackDto);
        Mockito.when(mapper.toDto(feedback)).thenCallRealMethod();

        FeedbackDto result = service.update(id, feedbackDto);

        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(3, result.getRating());
        Assertions.assertEquals("text1", result.getText());
    }

    @Test
    void updateExc() {
        UUID id = UUID.randomUUID();
        FeedbackDto feedback = new FeedbackDto();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(id, feedback));
    }

    @Test
    void getAll() {
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        List<Feedback> feedbacks = Arrays.asList(feedback1, feedback2);

        Mockito.when(repository.findAll()).thenReturn(feedbacks);
        Mockito.when(mapper.toDtos(feedbacks)).thenCallRealMethod();

        List<FeedbackDto> result = service.getAll();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getByCourseId() {
        UUID courseId = UUID.randomUUID();
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        feedback1.setCourseId(courseId);
        feedback2.setCourseId(courseId);
        List<Feedback> feedbacks = Arrays.asList(feedback1, feedback2);

        Mockito.when(repository.findAllByCourseId(courseId)).thenReturn(feedbacks);
        Mockito.when(mapper.toDtos(feedbacks)).thenCallRealMethod();

        List<FeedbackDto> result = service.getByCourseId(courseId);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getByUserId() {
        UUID userId = UUID.randomUUID();
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        feedback1.setCourseId(userId);
        feedback2.setCourseId(userId);
        List<Feedback> feedbacks = Arrays.asList(feedback1, feedback2);

        Mockito.when(repository.findAllByUserId(userId)).thenReturn(feedbacks);
        Mockito.when(mapper.toDtos(feedbacks)).thenCallRealMethod();

        List<FeedbackDto> result = service.getByUserId(userId);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void countRatingByCourse() {
        UUID courseId = UUID.randomUUID();
        Feedback feedback1 = new Feedback();
        Feedback feedback2 = new Feedback();
        feedback1.setCourseId(courseId);
        feedback2.setCourseId(courseId);
        feedback1.setRating(5);
        feedback2.setRating(3);
        List<Feedback> feedbacks = Arrays.asList(feedback1, feedback2);
        Mockito.when(repository.findAllByCourseId(courseId)).thenReturn(feedbacks);
        Mockito.when(repository.countAllByCourseIdAndRatingGreaterThan(courseId, 0)).thenReturn(2);

        Double result = service.countRatingByCourse(courseId);

        Assertions.assertEquals(4, result);

    }
}