package com.example.bllessons.web;

import com.example.bllessons.dto.LessonDto;
import com.example.bllessons.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/lessons")
@RestController
public class LessonController {
    private final LessonService service;

    @PostMapping
   public LessonDto create(@RequestBody LessonDto dto){
        return service.create(dto);
    }
    @GetMapping("/{id}")
    public LessonDto getById(@PathVariable UUID id){
        return service.getById(id);
    }
    @GetMapping
    public List<LessonDto> getAll(@RequestParam(name = "courseId") UUID courseId){
        return service.getAll(courseId);
    }
    @PutMapping("/{id}")
    public LessonDto update(@PathVariable UUID id,@RequestBody LessonDto dto){
        return service.update(id, dto);
    }

}
