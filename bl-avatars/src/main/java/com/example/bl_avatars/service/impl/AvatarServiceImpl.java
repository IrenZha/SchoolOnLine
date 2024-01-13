package com.example.bl_avatars.service.impl;

import com.example.bl_avatars.domain.Avatar;
import com.example.bl_avatars.dto.AvatarDto;
import com.example.bl_avatars.exc.FileErrors;
import com.example.bl_avatars.exc.FileSaveException;
import com.example.bl_avatars.mapper.AvatarMapper;
import com.example.bl_avatars.repository.AvatarRepository;
import com.example.bl_avatars.service.AvatarService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository repository;
    private final AvatarMapper mapper;

    @Override
    public AvatarDto save(MultipartFile file) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            Avatar avatar = new Avatar(filename, file.getContentType(), file.getBytes());
            return mapper.toDto(repository.save(avatar));

        } catch (Exception exc) {
            throw new FileSaveException(FileErrors.FILE_NOT_STORED, exc);
        }
    }



    @SneakyThrows
    @Override
    public AvatarDto getAvatar(String fileId) {
        Optional<Avatar> byId = repository.findById(fileId);
        if (byId.isEmpty()) {
            throw new FileNotFoundException(FileErrors.FILE_NOT_FOUND + fileId);
        } else {
            return mapper.toDto(byId.get());
        }
    }


    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
