package ru.deturpant.cloud.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.deturpant.cloud.store.entities.FolderEntity;
import ru.deturpant.cloud.store.entities.LinkEntity;
import ru.deturpant.cloud.store.entities.UserEntity;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
    boolean existsByUrl(String url);
    Optional<LinkEntity> findByUrl(String url);
}
