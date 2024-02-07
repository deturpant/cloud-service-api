package ru.deturpant.cloud.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.deturpant.cloud.store.entities.FavoriteEntity;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
}
