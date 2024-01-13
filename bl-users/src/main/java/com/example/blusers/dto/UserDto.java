package com.example.blusers.dto;

import com.example.blusers.domain.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Sex sex;
    private String avatarId;
    private Double wallet;
    private List<RoleDto> roles;

    public List<RoleDto> getRoles() {
        if (roles==null){
            return new ArrayList<>();
        }
        return roles;
    }
}
