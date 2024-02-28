package ru.deturpant.cloud.api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.deturpant.cloud.api.dto.UserDto;
import ru.deturpant.cloud.api.exceptions.BadRequestException;
import ru.deturpant.cloud.api.exceptions.NotFoundException;
import ru.deturpant.cloud.api.exceptions.UnauthorizedException;
import ru.deturpant.cloud.api.factories.UserDtoFactory;
import ru.deturpant.cloud.api.jwt.JwtAuthenticationResponse;
import ru.deturpant.cloud.api.jwt.JwtTokenProvider;
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
    BCryptPasswordEncoder passwordEncoder;


    @Qualifier("jwtTokenProvider")
    JwtTokenProvider tokenProvider;
    private static final String CREATE_USER = "/api/users";
    private static final String LOGIN = "/api/login";
    private static final String IS_AUTH = "/api/auth";
    private static final Integer DEFAULT_CAPACITY_GB = 20;
    @PostMapping(LOGIN)
    public ResponseEntity<?> login(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String password
    ) {
        UserEntity current_user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (passwordEncoder.matches(password, current_user.getPassword())) {
            String token = tokenProvider.generateToken(username);
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        }
        else {
            throw new UnauthorizedException("Invalid login/password");
        }
    }
    @GetMapping(IS_AUTH)
    public UserDto currentUser(HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        if (token != null && tokenProvider.validateToken(token)) {
            String user = tokenProvider.getUsernameFromToken(token);
            UserEntity current_user = userRepository
                    .findByUsername(user)
                    .orElseThrow(() -> new NotFoundException("User not found"));
            return userDtoFactory.makeUserDto(current_user);
        } else {
            throw new UnauthorizedException("Invalid token!!!!");
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
        String hashedPassword = passwordEncoder.encode(password);
        UserEntity user = userRepository.saveAndFlush(
                UserEntity.builder()
                        .username(username)
                        .email(email)
                        .password(hashedPassword)
                        .capacity(DEFAULT_CAPACITY_GB)
                        .registerAt(Instant.now())
                        .role(RoleEntity.USER)
                        .lastLoginAt(Instant.now())
                        .build()
        );

        return userDtoFactory.makeUserDto(user);
    }
}
