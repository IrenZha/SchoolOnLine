package com.example.il_platform.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private Sex sex;
    private List<RoleDto> roles;
    private String avatarId;
    private Double wallet = 0.0;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RoleDto> roles = getRoles();
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }
        return roles.stream()
                .map(roleDto -> roleDto.getName().name())
                .map(name -> (GrantedAuthority) () -> name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
