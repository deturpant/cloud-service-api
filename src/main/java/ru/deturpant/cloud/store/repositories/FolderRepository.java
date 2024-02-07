package ru.deturpant.cloud.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.deturpant.cloud.store.entities.FolderEntity;

public interface FolderRepository extends JpaRepository<FolderEntity, Long> {

}
