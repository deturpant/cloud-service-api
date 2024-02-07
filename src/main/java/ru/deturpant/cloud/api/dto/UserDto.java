package ru.deturpant.cloud.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.deturpant.cloud.store.entities.RoleEntity;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;

    String username;

    RoleEntity role;

    @JsonProperty("created_at")
    Instant createdAt;
}
