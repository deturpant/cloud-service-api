package ru.deturpant.cloud.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileDto {
    Long id;

    String name;

    Integer size;

    @JsonProperty("created_at")
    Instant createdAt;
}
