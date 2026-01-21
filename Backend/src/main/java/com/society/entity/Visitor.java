package com.society.entity;



/* ========= JPA ========= */
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

/* ========= JAVA ========= */
import java.time.LocalDateTime;

import com.society.entityenum.VisitPurpose;

/* ========= LOMBOK ========= */
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "visitor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer visitorId;

    @Column(nullable = false)
    private String visitorName;

    private String phone;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    @Enumerated(EnumType.STRING)
    private VisitPurpose purpose;

    @ManyToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;
}

