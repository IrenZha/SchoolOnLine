package com.example.il_platform.dto.lesson;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
public class MaterialDto {
    private UUID id;
    private String name;
    private String link;

    @ToString.Exclude
    private LessonDto lesson;
}
