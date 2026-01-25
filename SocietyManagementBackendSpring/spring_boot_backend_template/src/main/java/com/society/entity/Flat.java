package com.society.entity;

import com.society.entityenum.FlatStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "flat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flatId;

    @Column(nullable = false, length = 10)
    private String flatNumber;

    @Column(nullable = false, length = 10)
    private String towerName;

    @Column
    private Double areaSqft;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlatStatus status;

    // Flat belongs to ONE society
    @ManyToOne
    @JoinColumn(name = "society_id", nullable = false)
    private Society society;
}
