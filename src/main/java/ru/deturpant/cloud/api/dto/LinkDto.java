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
public class LinkDto {
    Long id;

    String url;

    @JsonProperty("created_at")
    Instant createdAt;
}
