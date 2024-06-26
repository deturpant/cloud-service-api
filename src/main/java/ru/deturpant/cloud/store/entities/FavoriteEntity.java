package ru.deturpant.cloud.store.entities;

import jakarta.persistence.*;
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

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

    @Builder.Default
    private Instant createdAt = Instant.now();
}
