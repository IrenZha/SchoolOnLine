package com.example.il_platform.client;

import com.example.il_platform.dto.feedback.FeedbackDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "feedback-client", url = "${client.feedbacks}")
public interface FeedbackClient {
    @PostMapping
    FeedbackDto save(@RequestBody FeedbackDto feedbackDto);

    @GetMapping("/{id}")
    FeedbackDto getById(@PathVariable(name = "id") UUID id);

    @GetMapping("/course/{courseId}")
    List<FeedbackDto> getAllByCourseId(@PathVariable(name = "courseId") UUID courseId);

    @GetMapping()
    List<FeedbackDto> getAllByUserId(@RequestParam(name = "userId") UUID userId);

    @GetMapping("/all")
    List<FeedbackDto> getAll();

    @PutMapping("/{id}")
    FeedbackDto update(@PathVariable(name = "id") UUID id,
                       @RequestBody FeedbackDto dto);

    @GetMapping("/rating")
    Double countRatingByCourse(@RequestParam(name = "courseId") UUID courseId);
}
