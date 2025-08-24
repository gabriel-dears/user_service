package com.hospital_app.user_service.infra.adapter.out.db.jpa.user;

import com.hospital_app.user_service.domain.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class JpaUserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    @Size(min = 5, max = 50)
    private String username;

    @Column(unique = true)
    @Email
    @Size(max = 100)
    String email;

    @Size(min = 10, max = 255)
    private String passwordHash;

    @Column(nullable = false)
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
