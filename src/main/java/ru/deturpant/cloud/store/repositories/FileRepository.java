package ru.deturpant.cloud.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.deturpant.cloud.store.entities.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    void deleteById(Long id);
}
