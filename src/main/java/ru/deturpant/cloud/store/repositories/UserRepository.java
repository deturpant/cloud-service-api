package ru.deturpant.cloud.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.deturpant.cloud.store.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
