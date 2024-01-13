package com.example.il_platform.dto.avatar;

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
   private String fileUri;
}
