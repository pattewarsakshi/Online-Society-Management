package com.society.management.entity;

import com.society.management.enumtype.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * User entity represents all system users:
 * SUPER_ADMIN, ADMIN, OWNER, TENANT, GUARD
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // Full name of user
    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String fullName;

    // Email is used for login
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    // Indian phone number validation
    @NotBlank
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number")
    @Column(nullable = false, unique = true)
    private String phone;

    // User role
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Encrypted password
    @NotBlank
    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    // Many users belong to one society
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id")
    private Society society;
}
