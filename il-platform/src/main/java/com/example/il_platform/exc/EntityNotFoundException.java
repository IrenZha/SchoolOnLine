package com.example.il_platform.exc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityNotFoundException extends RuntimeException {
    private String entity;
}
