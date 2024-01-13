package com.example.il_platform.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private UUID id;
    private String avatarId;
    private UUID authorId;
    private String title;
    private String description;
    private Double price = 0.00;
    private Complexity complexity;
    private CategoryDto category;
}
