package ru.deturpant.cloud.api.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.deturpant.cloud.api.dto.UserDto;
import ru.deturpant.cloud.api.exceptions.BadRequestException;
import ru.deturpant.cloud.api.factories.UserDtoFactory;
import ru.deturpant.cloud.store.entities.RoleEntity;
import ru.deturpant.cloud.store.entities.UserEntity;
import ru.deturpant.cloud.store.repositories.UserRepository;

import java.time.Instant;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserDtoFactory userDtoFactory;
    UserRepository userRepository;
    private static final String CREATE_USER = "/api/users";
    private static final String LOGIN = "/api/login";
    private static final Integer DEFAULT_CAPACITY_GB = 20;
    @PostMapping(LOGIN)
    public UserDto login(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String password
    ) {
        UserEntity current_user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));
        if (Objects.equals(current_user.getPassword(), password)) {
            return userDtoFactory.makeUserDto(current_user);
        }
        else {
            throw new BadRequestException("Invalid login/password");
        }
    }
    @PostMapping(CREATE_USER)
    public UserDto createUser(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String email,
            @RequestParam(required = true) String password
    ) {
        userRepository
                .findByEmail(email)
                .ifPresent(user -> {
                    throw new BadRequestException(String.format("User (email) \"%s\"  already exists", email));
                });
        userRepository
                .findByUsername(username)
                .ifPresent(user -> {
                    throw new BadRequestException(String.format("User (username) \"%s\" already exists", username));
                });

        UserEntity user = userRepository.saveAndFlush(
                UserEntity.builder()
                        .username(username)
                        .email(email)
                        .password(password)
                        .capacity(DEFAULT_CAPACITY_GB)
                        .registerAt(Instant.now())
                        .role(RoleEntity.USER)
                        .lastLoginAt(Instant.now())
                        .build()
        );

        return userDtoFactory.makeUserDto(user);
    }
}
