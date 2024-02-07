package ru.deturpant.cloud.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "folder")
public class FolderEntity {
    @Id
    private Long id;

    private String name;

    private int size;


    @ManyToOne
    @JoinColumn(name="root_folder_id")
    private FolderEntity root_folder;

    @OneToOne
    @JoinColumn(name="owner_id")
    private UserEntity owner;

    @OneToMany
    private List<FileEntity> files;

    private Instant createdAt = Instant.now();

    private Instant modifiedAt;

}
