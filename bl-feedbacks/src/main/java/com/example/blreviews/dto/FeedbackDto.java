package com.example.blreviews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    private UUID id;
    private UUID userId;
    private UUID courseId;
    private Integer rating;
    private String text;
}
