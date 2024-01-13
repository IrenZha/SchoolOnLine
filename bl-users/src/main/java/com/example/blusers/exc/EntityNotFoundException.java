package com.example.blusers.exc;

import lombok.*;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityNotFoundException extends RuntimeException {
private String entity;


}
