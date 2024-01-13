package com.example.il_platform.dto.feedback;

import lombok.Data;

import java.util.UUID;

@Data
public class FeedbackDto {
    private UUID id;
    private UUID userId;
    private UUID courseId;
    private Integer rating = 0;
    private String text;
}
