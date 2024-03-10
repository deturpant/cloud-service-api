package ru.deturpant.cloud.api.requests;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileUploadRequest {
    private Long folderId;
    private MultipartFile file;
}
