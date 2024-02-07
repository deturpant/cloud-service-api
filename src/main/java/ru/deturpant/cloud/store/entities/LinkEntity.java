package ru.deturpant.cloud.store.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity
public class LinkEntity {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

    private Instant createdAt = Instant.now();
}
