package ru.deturpant.cloud.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.deturpant.cloud.store.entities.FolderEntity;
import ru.deturpant.cloud.store.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<FolderEntity, Long> {
    Optional<FolderEntity> findByOwnerAndRootFolderIsNull(UserEntity owner);
    List<FolderEntity> findAllByOwner_Id(Long userId);
    List<FolderEntity> findByRootFolderId(Long rootFolderId);
}
