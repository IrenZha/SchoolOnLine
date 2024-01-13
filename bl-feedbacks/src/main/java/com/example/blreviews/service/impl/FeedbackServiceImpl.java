package com.example.blreviews.service.impl;

import com.example.blreviews.domain.Feedback;
import com.example.blreviews.dto.FeedbackDto;
import com.example.blreviews.exc.EntityNotFoundException;
import com.example.blreviews.mapper.FeedbackMapper;
import com.example.blreviews.repository.FeedbackRepository;
import com.example.blreviews.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository repository;
    private final FeedbackMapper mapper;

    @Override
    public FeedbackDto save(FeedbackDto feedbackDto) {
        return mapper.toDto(repository.save(mapper.toEntity(feedbackDto)));
    }

    @Override
    public FeedbackDto getById(UUID id) {
        Optional<Feedback> byId = repository.findById(id);
        if (byId.isPresent()) {
            Feedback feedback = byId.get();
            return mapper.toDto(feedback);
        } else throw new EntityNotFoundException(id.toString());
    }

    @Override
    public FeedbackDto update(UUID id, FeedbackDto feedbackDto) {
        Optional<Feedback> byId = repository.findById(id);
        if (byId.isPresent()) {
            Feedback feedback = byId.get();
            mapper.update(feedback, feedbackDto);
            return mapper.toDto(feedback);
        } else throw new EntityNotFoundException(id.toString());
    }

    @Override
    public List<FeedbackDto> getAll() {
        return mapper.toDtos(repository.findAll());
    }

    @Override
    public List<FeedbackDto> getByCourseId(UUID courseId) {
        return mapper.toDtos(repository.findAllByCourseId(courseId));
    }

    @Override
    public List<FeedbackDto> getByUserId(UUID userId) {
        return mapper.toDtos(repository.findAllByUserId(userId));
    }

    @Override
    public Double countRatingByCourse(UUID courseId) {
        List<Feedback> allByCourseId = repository.findAllByCourseId(courseId);
        Double sum = (double)allByCourseId.stream().mapToInt(Feedback::getRating).sum();
        Double count = (double)repository.countAllByCourseIdAndRatingGreaterThan(courseId, 0);
        return sum / count;
    }
}
