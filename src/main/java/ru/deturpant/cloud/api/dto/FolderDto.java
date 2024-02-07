package ru.deturpant.cloud.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderDto {
    Long id;

    String name;

    Integer size;

    @JsonProperty("created_at")
    Instant createdAt;

}
