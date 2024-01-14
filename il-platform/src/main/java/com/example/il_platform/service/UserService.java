package com.example.il_platform.service;

import com.example.il_platform.dto.feedback.FeedbackDto;
import com.example.il_platform.dto.users.RoleEnum;
import com.example.il_platform.dto.users.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto save(UserDto user);

    UserDto getById(UUID id);

    List<UserDto> getAllUsers();

    UserDto setAvatar(UserDto user, MultipartFile file);

    UserDto findByUsername(String username);

    UserDto becomeTeacher(UUID userId);

    List<FeedbackDto> getAllFeedbacks();

    UserDto update(UUID userId, UserDto dto);

    UserDto setWallet(UserDto dto, Double sum);

    List<FeedbackDto> getAllFeedbacksByUserId(UUID userId);

    boolean isPresentRole(UserDto dto, RoleEnum role);

    void feedbackUpdate(UUID feedbackId, FeedbackDto feedbackDto);

    void feedbackSave(FeedbackDto feedbackDto);

    List<UserDto> teachers();
}
