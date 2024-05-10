package ru.deturpant.cloud.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="folder_id")
    private FolderEntity folder;

    private String name;

    private Integer size;

    private String mime_type;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private String path;

    private Instant modifiedAt;

    @OneToMany(mappedBy = "file")
    @Builder.Default
    private List<LinkEntity> links = new ArrayList<>();

    @OneToOne
    @Builder.Default
    private FavoriteEntity favorite = null;
}
