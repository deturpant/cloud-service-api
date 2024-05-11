package ru.deturpant.cloud.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private int size;


    @ManyToOne
    private FolderEntity rootFolder;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private UserEntity owner;

    @OneToMany(mappedBy = "folder")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @Builder.Default
    private List<FileEntity> files = new ArrayList<>();

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant modifiedAt;

}
