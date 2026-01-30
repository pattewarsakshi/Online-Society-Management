package com.society.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Society entity represents a housing society.
 * This class is ONLY responsible for database mapping.
 */
@Entity
@Table(name = "societies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Society {

    /**
     * Primary key for society
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long societyId;

    /**
     * Name of the society
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Full address of the society
     */
    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, length = 6)
    private String pincode;

    /**
     * Timestamp when society was created
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * One society can have many users
     */
    @OneToMany(mappedBy = "society", fetch = FetchType.LAZY)
    private List<User> users;

    /**
     * Automatically set creation time
     */
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

