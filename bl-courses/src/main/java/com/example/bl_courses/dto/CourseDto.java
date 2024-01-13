package com.example.bl_courses.dto;

import com.example.bl_courses.domain.Category;
import com.example.bl_courses.domain.Complexity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private UUID id;
    private UUID avatarId;
    private UUID authorId;
    private String title;
    private String description;
    private Double price;
    private Complexity complexity;
    private Category category;
}
