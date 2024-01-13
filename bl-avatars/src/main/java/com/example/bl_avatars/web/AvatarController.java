package com.example.bl_avatars.web;

import com.example.bl_avatars.dto.AvatarDto;
import com.example.bl_avatars.service.AvatarService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService service;

    @PostMapping("/upload")
    public AvatarDto uploadFile(@RequestPart("file") MultipartFile file) {
        return service.save(file);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        AvatarDto avatarDto = service.getAvatar(fileId);
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + avatarDto.getFileName()).
                body(new ByteArrayResource(avatarDto.getFileData()));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam(name = "id") String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}