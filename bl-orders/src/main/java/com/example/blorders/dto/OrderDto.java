package com.example.blorders.dto;

import com.example.blorders.domain.Status;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;
    private UUID userId;
    private UUID courseId;
    private Status status;
    private Date dateCreation;
    private Date dateUpdate;
}
