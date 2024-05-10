package ru.deturpant.cloud.api.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.Response;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.deturpant.cloud.api.dto.FileDto;
import ru.deturpant.cloud.api.exceptions.BadRequestException;
import ru.deturpant.cloud.api.exceptions.NotFoundException;
import ru.deturpant.cloud.api.factories.FileDtoFactory;
import ru.deturpant.cloud.api.requests.FileUploadRequest;
import ru.deturpant.cloud.store.entities.FileEntity;
import ru.deturpant.cloud.store.entities.FolderEntity;
import ru.deturpant.cloud.store.repositories.FileRepository;
import ru.deturpant.cloud.store.repositories.FolderRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    static final String DOWNLOAD_FILE = "/api/files/{file_id}";

    @DeleteMapping(DOWNLOAD_FILE)
    public ResponseEntity<String> deleteFile(
            @PathVariable("file_id") Long fileId
    ) {
        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("File not found!"));
        fileRepository.deleteById(file.getId());
        return ResponseEntity.ok().body("{\"status\": \"OK\", \"message\": \"File deleted successfully\"}");
    }

    @GetMapping(DOWNLOAD_FILE)
    public ResponseEntity<Resource> downloadFile(
            @PathVariable("file_id") Long rootFolderId
    ) {
        FileEntity file = fileRepository.findById(rootFolderId)
                .orElseThrow(() -> new NotFoundException("File not found!"));
        try {
            Path pathFile = Paths.get(file.getPath());
            Resource resource = new UrlResource(pathFile.toUri());
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, file.getMime_type());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }
    }

    @PostMapping(UPLOAD_FILE)
    public FileDto uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder_id") Long folderId)
    {
        if (file.isEmpty()) {
            throw new BadRequestException("Uploaded file is empty");
        }
        FolderEntity folder = folderRepository.findById(folderId)
                .orElseThrow( () -> new NotFoundException("Folder does not exist"));
        String userId = folder.getOwner().getId().toString();
        int fileSize = (int) file.getSize();
        String mimeType = file.getContentType();
        String basePath = "/var/www/cloud-storage/";
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
