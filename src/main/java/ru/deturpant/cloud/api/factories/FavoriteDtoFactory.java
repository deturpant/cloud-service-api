package ru.deturpant.cloud.api.factories;

import org.springframework.stereotype.Component;
import ru.deturpant.cloud.api.dto.FavoriteDto;
import ru.deturpant.cloud.store.entities.FavoriteEntity;

@Component
public class FavoriteDtoFactory {
    public FavoriteDto makeFavoriteDto(FavoriteEntity entity) {
        return FavoriteDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
