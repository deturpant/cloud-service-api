package ru.deturpant.cloud.store.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "favorite")
public class FavoriteEntity {
    @Id
    private Long id;

    @ManyToOne
    private FileEntity file;

    @Builder.Default
    private Instant createdAt = Instant.now();
}
