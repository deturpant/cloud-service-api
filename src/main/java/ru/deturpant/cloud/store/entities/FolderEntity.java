package ru.deturpant.cloud.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Builder.Default
    private List<FileEntity> files = new ArrayList<>();

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant modifiedAt;

}
