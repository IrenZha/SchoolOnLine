package com.example.il_platform.client;

import com.example.il_platform.config.FeignSupportConfig;
import com.example.il_platform.dto.avatar.AvatarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "avatar-client", url = "${client.avatars}", configuration = FeignSupportConfig.class)
public interface AvatarClient {
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    AvatarDto uploadFile(@RequestPart("file") MultipartFile file);

    @GetMapping("/allAvatars")
    List<AvatarDto> getAllAvatars();

}
