package com.society.management.entity;
import com.society.management.enumtype.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * User entity represents all system users:
 * Super Admin, Admin, Owner, Tenant, Guard
 */
@Entity
@Table(name = "users") // "user" is reserved keyword in MySQL
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Primary key for User table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * Full name of the user
     */
    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 to 100 characters")
    @Column(nullable = false)
    private String fullName;

    /**
     * Email is used as username for login
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Phone number (10 digits for India)
     */
    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Invalid Indian phone number"
    )
    @Column(nullable = false, unique = true)
    private String phone;

    /**
     * Role of user (ADMIN, OWNER, etc.)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * Encrypted password (never store plain text)
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(nullable = false)
    private String password;

    /**
     * Society to which user belongs
     * Many users can belong to one society
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "society_id")
    private Society society;
}



