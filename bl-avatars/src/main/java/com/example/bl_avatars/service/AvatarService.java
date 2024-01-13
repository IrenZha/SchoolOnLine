package com.example.bl_avatars.service;

import com.example.bl_avatars.dto.AvatarDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AvatarService {
AvatarDto save(MultipartFile file);
AvatarDto getAvatar(String fileId);
void delete(String id);
}
