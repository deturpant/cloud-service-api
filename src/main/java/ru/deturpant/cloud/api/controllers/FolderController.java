package ru.deturpant.cloud.api.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.deturpant.cloud.api.dto.FolderDto;
import ru.deturpant.cloud.api.exceptions.BadRequestException;
import ru.deturpant.cloud.api.exceptions.NotFoundException;
import ru.deturpant.cloud.api.factories.FolderDtoFactory;
import ru.deturpant.cloud.store.entities.FolderEntity;
import ru.deturpant.cloud.store.entities.UserEntity;
import ru.deturpant.cloud.store.repositories.FolderRepository;
import ru.deturpant.cloud.store.repositories.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FolderController {
    FolderDtoFactory folderDtoFactory;
    FolderRepository folderRepository;
    UserRepository userRepository;
    static final String CREATE_FOLDER = "/api/folders";
    static final String GET_USER_ROOT_FOLDER = "/api/users/{user_id}/root-folder";
    static final String GET_FOLDERS_BY_ROOT_FOLDER = "/api/folders/root/{root_folder_id}";
    @PostMapping(CREATE_FOLDER)
    public FolderDto createFolder(
            @RequestParam(required = true) String name,
            @RequestParam(required = true) Long owner_id,
            @RequestParam(required = false) Long root_folder_id
    ) {
        UserEntity user = userRepository.findById(owner_id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        FolderEntity rootFolder = null;
        if (root_folder_id != null) {
            rootFolder = folderRepository.findById(root_folder_id)
                    .orElseThrow(() -> new BadRequestException("Root folder with id " + root_folder_id + " not found."));
        }
        FolderEntity folderEntity = FolderEntity.builder()
                .rootFolder(rootFolder)
                .owner(user)
                .name(name)
                .createdAt(Instant.now())
                .modifiedAt(Instant.now())
                .build();
        folderEntity = folderRepository.saveAndFlush(folderEntity);
        return folderDtoFactory.makeFolderDto(folderEntity);
    }
    @GetMapping(GET_USER_ROOT_FOLDER)
    public FolderDto getUserRootFolder(@PathVariable("user_id") Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        FolderEntity rootFolder = folderRepository.findByOwnerAndRootFolderIsNull(user)
                .orElseThrow(() -> new NotFoundException("Root folder not found for user with id " + userId));

        return folderDtoFactory.makeFolderDto(rootFolder);
    }
    @GetMapping(GET_FOLDERS_BY_ROOT_FOLDER)
    public List<FolderDto> getFoldersByRootFolder(@PathVariable("root_folder_id") Long rootFolderId) {
        List<FolderEntity> folders = folderRepository.findByRootFolderId(rootFolderId);
        return folders.stream()
                .map(folderDtoFactory::makeFolderDto)
                .collect(Collectors.toList());
    }
}
