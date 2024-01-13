package com.example.il_platform.service.impl;

import com.example.il_platform.client.AvatarClient;
import com.example.il_platform.client.CourseClient;
import com.example.il_platform.client.FeedbackClient;
import com.example.il_platform.client.UserClient;
import com.example.il_platform.dto.avatar.AvatarDto;
import com.example.il_platform.dto.course.CourseDto;
import com.example.il_platform.dto.feedback.FeedbackDto;
import com.example.il_platform.dto.users.RoleDto;
import com.example.il_platform.dto.users.RoleEnum;
import com.example.il_platform.dto.users.UserDto;
import com.example.il_platform.exc.PasswordMatchException;
import com.example.il_platform.exc.UsernameExistsException;
import com.example.il_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserClient userClient;
    private final AvatarClient avatarClient;
    private final FeedbackClient feedbackClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto save(UserDto user) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new PasswordMatchException();
        }
        UserDto byUsername = userClient.findByUsername(user.getUsername());
        if (byUsername != null) {
            throw new UsernameExistsException(user.getUsername());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userClient.save(user);
    }

    @Override
    public UserDto getById(UUID id) {
        return userClient.getById(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userClient.getAll();
    }


    @Override
    public UserDto setAvatar(UserDto user, MultipartFile file) {
        AvatarDto avatarDto = avatarClient.uploadFile(file);
        String fileId = avatarDto.getFileId();
        user.setAvatarId(fileId);
        userClient.update(user.getId(), user);
        return user;
    }

    @Override
    public UserDto findByUsername(String username) {
        return userClient.findByUsername(username);
    }

    @Override
    public UserDto becomeTeacher(UUID userId) {
        UserDto userDto = userClient.getById(userId);
        if (!isPresentRole(userDto, RoleEnum.ROLE_TEACHER)) {
            return userClient.addRole(userId, RoleEnum.ROLE_TEACHER.name());
        }
        return userDto;
    }

    @Override
    public List<FeedbackDto> getAllFeedbacks() {
        return feedbackClient.getAll();
    }

    @Override
    public UserDto update(UUID userId, UserDto dto) {
        return userClient.update(userId, dto);
    }

    @Override
    public UserDto setWallet(UserDto dto, Double sum) {
        dto.setWallet(dto.getWallet() + sum);
        return userClient.update(dto.getId(), dto);
    }

    @Override
    public List<FeedbackDto> getAllFeedbacksByUserId(UUID userId) {
        return feedbackClient.getAllByUserId(userId);
    }

    @Override
    public void feedbackUpdate(UUID feedbackId, FeedbackDto feedbackDto) {
        feedbackClient.update(feedbackId, feedbackDto);
    }

    @Override
    public List<UserDto> teachers() {
        return userClient.getAllByRole("ROLE_TEACHER");
    }

    @Override
    public boolean isPresentRole(UserDto dto, RoleEnum role) {
        Optional<RoleDto> first = dto.getRoles()
                .stream()
                .filter(roleDto -> roleDto.getName().equals(role))
                .findFirst();
        return first.isPresent();
    }
}
