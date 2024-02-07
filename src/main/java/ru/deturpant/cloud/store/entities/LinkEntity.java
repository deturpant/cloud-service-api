package ru.deturpant.cloud.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "folder")
public class LinkEntity {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

    private Instant createdAt = Instant.now();
}
