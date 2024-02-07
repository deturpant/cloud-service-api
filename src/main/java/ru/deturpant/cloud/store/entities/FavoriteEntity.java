package ru.deturpant.cloud.store.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity
public class FavoriteEntity {
    @Id
    private Long id;

    @ManyToOne
    private FileEntity file;

    private Instant createdAt = Instant.now();
}
