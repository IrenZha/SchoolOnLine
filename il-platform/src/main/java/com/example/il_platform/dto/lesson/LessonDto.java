package com.example.il_platform.dto.lesson;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
public class LessonDto {
    private UUID id;
    private String theme;
    private String text;
    private UUID courseId;

}
