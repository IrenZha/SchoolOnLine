package com.example.il_platform.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private UUID id;
    private RoleEnum name;

    public RoleDto(RoleEnum name) {
        this.name = name;
    }
}
