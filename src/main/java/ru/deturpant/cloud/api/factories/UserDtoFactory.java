package ru.deturpant.cloud.api.factories;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import ru.deturpant.cloud.api.dto.UserDto;
import ru.deturpant.cloud.store.entities.UserEntity;

@Component
public class UserDtoFactory {

    public UserDto makeUserDto(UserEntity entity) {
        return UserDto
                .builder()
                .id(entity.getId())
                .role(entity.getRole())
                .username(entity.getUsername())
                .createdAt(entity.getRegisterAt())
                .build();

    }
}
