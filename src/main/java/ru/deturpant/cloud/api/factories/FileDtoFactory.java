package ru.deturpant.cloud.api.factories;

import org.springframework.stereotype.Component;
import ru.deturpant.cloud.api.dto.FileDto;
import ru.deturpant.cloud.store.entities.FileEntity;

@Component
public class FileDtoFactory {
    public FileDto makeFileDto(FileEntity entity) {
        return FileDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .name(entity.getName())
                .size(entity.getSize())
                .build();
    }
}
