package ru.deturpant.cloud.api.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.deturpant.cloud.api.dto.FileDto;
import ru.deturpant.cloud.api.exceptions.BadRequestException;
import ru.deturpant.cloud.api.exceptions.NotFoundException;
import ru.deturpant.cloud.api.factories.FileDtoFactory;
import ru.deturpant.cloud.store.entities.FileEntity;
import ru.deturpant.cloud.store.entities.FolderEntity;
import ru.deturpant.cloud.store.repositories.FileRepository;
import ru.deturpant.cloud.store.repositories.FolderRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@RestController
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileController {
    FileDtoFactory fileDtoFactory;
    FileRepository fileRepository;
    FolderRepository folderRepository;
    static final String UPLOAD_FILE = "/api/files/upload";

    @PostMapping
    public FileDto uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("folder_id") Long folderId
            )
    {
        if (file.isEmpty()) {
            throw new BadRequestException("Uploaded file is empty");
        }
        FolderEntity folder = folderRepository.findById(folderId)
                .orElseThrow( () -> new NotFoundException("Folder does not exist"));
        String userId = folder.getOwner().getId().toString();
        int fileSize = (int) file.getSize();
        String mimeType = file.getContentType();
        String basePath = "/var/www/cloud-service/";
        String folderPath = basePath + userId + "/" + folderId + "/";
        String originalFilename = file.getOriginalFilename();
        String filePath = folderPath + originalFilename;
        try {
            Path path = Paths.get(folderPath);
            Files.createDirectories(path);
            File saveFile = new File(filePath);
            file.transferTo(saveFile);

            FileEntity fileEntity = FileEntity.builder()
                    .createdAt(Instant.now())
                    .name(originalFilename)
                    .size(fileSize)
                    .modifiedAt(Instant.now())
                    .mime_type(mimeType)
                    .path(filePath)
                    .folder(folder)
                    .build();
            fileRepository.saveAndFlush(fileEntity);
            return fileDtoFactory.makeFileDto(fileEntity);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

    }
}
