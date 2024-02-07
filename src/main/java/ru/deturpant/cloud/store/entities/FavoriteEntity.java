package ru.deturpant.cloud.store.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "favorite")
public class FavoriteEntity {
    @Id
    private Long id;

    @ManyToOne
    private FileEntity file;

    private Instant createdAt = Instant.now();
}
