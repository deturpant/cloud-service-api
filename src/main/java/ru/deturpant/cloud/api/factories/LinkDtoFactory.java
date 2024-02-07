package ru.deturpant.cloud.api.factories;

import org.springframework.stereotype.Component;
import ru.deturpant.cloud.api.dto.LinkDto;
import ru.deturpant.cloud.store.entities.LinkEntity;

@Component
public class LinkDtoFactory {
    public LinkDto makeLinkDto(LinkEntity entity) {
        return
                LinkDto.builder()
                        .id(entity.getId())
                        .url(entity.getUrl())
                        .createdAt(entity.getCreatedAt())
                        .build();
    }
}
