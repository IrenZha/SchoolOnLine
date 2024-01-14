package com.example.blreviews.web;

import com.example.blreviews.dto.FeedbackDto;
import com.example.blreviews.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {
    private final FeedbackService service;

    @PostMapping
    public FeedbackDto save(@RequestBody FeedbackDto feedbackDto) {
        return service.save(feedbackDto);
    }

    @GetMapping("/{id}")
    public FeedbackDto getById(@PathVariable(name = "id") UUID id) {
        return service.getById(id);
    }

    @GetMapping("/course/{courseId}")
    public List<FeedbackDto> getAllByCourseId(@PathVariable(name = "courseId") UUID courseId) {
        return service.getByCourseId(courseId);
    }

    @GetMapping()
    public List<FeedbackDto> getAllByUserId(@RequestParam(name = "userId") UUID userId) {
        return service.getByUserId(userId);
    }

    @GetMapping("/all")
    public List<FeedbackDto> getAll(){
        return service.getAll();
    }

    @PutMapping("/{id}")
    public FeedbackDto update(@PathVariable(name = "id")UUID id,
                              @RequestBody FeedbackDto dto){
        return service.update(id,dto);
    }

    @GetMapping("/rating")
    public Double countRatingByCourse(@RequestParam(name = "courseId") UUID courseId){
       return service.countRatingByCourse(courseId);
    }
}