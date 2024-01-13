package com.example.bl_avatars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvatarDto {
    private String fileId;
    private String fileName;
    private String fileType;
    private byte[] fileData;
}
