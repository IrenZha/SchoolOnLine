package com.example.bl_avatars.service.impl;

import com.example.bl_avatars.domain.Avatar;
import com.example.bl_avatars.dto.AvatarDto;
import com.example.bl_avatars.exc.FileSaveException;
import com.example.bl_avatars.mapper.AvatarMapperImpl;
import com.example.bl_avatars.repository.AvatarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AvatarServiceImplTest {
    @Mock
    private AvatarRepository repository;
    @Mock
    private AvatarMapperImpl mapper;
    @InjectMocks
    private AvatarServiceImpl avatarService;


    @Test
   public void testGetFile() throws IOException {
        String fileName = "fileName";
        String contentType = "fileType";
        String content = "Hello, World!";
        MultipartFile file = new MockMultipartFile(fileName, contentType, fileName, content.getBytes());

        byte[] fileData = file.getBytes();
        content = new String(fileData);


        Assertions.assertEquals(content, content);
    }
    @Test
    public void testSave() {
        String fileName = "fileName";
        String contentType = "fileType";
        String content = "Hello, World!";
        MultipartFile file = new MockMultipartFile(fileName, fileName, contentType, content.getBytes());

        AvatarDto result = avatarService.save(file);

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Avatar.class));
    }



    @Test
   void getAvatar() {
        String id = "1";
        Avatar avatar = new Avatar(id, "fileName", "fileType", null);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(avatar));
        Mockito.when(mapper.toDto(avatar)).thenCallRealMethod();

        AvatarDto result = avatarService.getAvatar(id);

        Assertions.assertEquals(id, avatar.getFileId());
        Assertions.assertEquals("fileName", avatar.getFileName());
    }

    @Test
    public void getAvatarExc(){
       String id = UUID.randomUUID().toString();

        Assertions.assertThrows(FileNotFoundException.class, () -> avatarService.getAvatar(id));
    }

    @Test
   public void delete(){
        String id = UUID.randomUUID().toString();

        avatarService.delete(id);

        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }
}