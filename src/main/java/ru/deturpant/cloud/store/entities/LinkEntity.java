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
@Table(name = "folder")
public class LinkEntity {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

    @Builder.Default
    private Instant createdAt = Instant.now();
}
