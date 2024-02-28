package ru.deturpant.cloud.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // SEQUENCE -> ID уникальны на уровне БД
    private Long id;

    private String username;

    @Enumerated(EnumType.ORDINAL)
    private RoleEntity role;

    private String email;

    private String password;

    private String phone_number;

    private Integer capacity;

    private String Location;

    private String bio;

    @Builder.Default
    private Instant registerAt = Instant.now();

    private Instant lastLoginAt;

    @OneToMany(mappedBy = "owner")
    @Builder.Default
    private List<FolderEntity> folders = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
