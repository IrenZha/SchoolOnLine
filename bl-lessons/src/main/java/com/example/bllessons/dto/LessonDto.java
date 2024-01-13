package com.example.bllessons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    private UUID id;
    private String theme;
    private String text;
    private UUID courseId;

}
