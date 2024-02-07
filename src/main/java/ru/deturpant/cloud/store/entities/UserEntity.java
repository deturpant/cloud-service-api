package ru.deturpant.cloud.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // SEQUENCE -> ID уникальны на уровне БД
    private Long id;

    private String username;

    @Enumerated(EnumType.ORDINAL)
    private RoleEntity role;

    private String email;

    private String password;

    private String phone_number;

    private Integer capacity;

    private String Location;

    private String bio;

    private Instant registerAt = Instant.now();

    private Instant lastLoginAt;

    @OneToMany
    private List<FolderEntity> folders;

}
