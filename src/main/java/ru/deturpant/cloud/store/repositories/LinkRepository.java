package ru.deturpant.cloud.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.deturpant.cloud.store.entities.LinkEntity;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
    boolean existsByUrl(String url);
}
