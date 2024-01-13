package com.example.il_platform.client;

import com.example.il_platform.dto.lesson.LessonDto;
import com.example.il_platform.dto.lesson.MaterialDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@FeignClient(name = "lesson-client", url = "${client.lessons}")
public interface LessonClient {
    @PostMapping
    LessonDto create(@RequestBody LessonDto dto);
    @GetMapping("/{id}")
    LessonDto getById(@PathVariable UUID id);
    @GetMapping
    List<LessonDto> getAll(@RequestParam(name = "courseId") UUID courseId);
    @PutMapping("/{id}")
    LessonDto update(@PathVariable UUID id,@RequestBody LessonDto dto);

}
