package ru.deturpant.cloud.store.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
public class FileEntity {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private FolderEntity folder;

    private String name;

    private Integer size;

    private String mime_type;

    private Instant createdAt = Instant.now();

    private Instant modifiedAt;

    @OneToMany
    private List<LinkEntity> links;

    @OneToMany
    private List<FavoriteEntity> favorites;
}
