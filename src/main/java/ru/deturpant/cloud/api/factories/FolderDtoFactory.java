package ru.deturpant.cloud.api.factories;

import org.springframework.stereotype.Component;
import ru.deturpant.cloud.api.dto.FolderDto;
import ru.deturpant.cloud.store.entities.FolderEntity;

@Component
public class FolderDtoFactory {
    public FolderDto makeFolderDto(FolderEntity entity) {
        return FolderDto.builder()
                .id(entity.getId())
                .size(entity.getSize())
                .createdAt(entity.getCreatedAt())
                .name(entity.getName())
                .build();
    }
}
