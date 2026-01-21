package com.society.entity;

/* ========= JPA / HIBERNATE ========= */
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

/* ========= LOMBOK ========= */
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/* ========= JACKSON ========= */
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.society.entityenum.ComplaintStatus;

/* ========= JAVA ========= */
import java.time.LocalDate;

@Entity
@Table(name = "complaint")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private Integer complaintId;

    // User (Resident) who raised the complaint
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    // Flat related to the complaint
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @JsonBackReference
    private Flat property;

    @Column(name = "complaint_type", length = 50, nullable = false)
    private String complaintType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus status;

    @Column(name = "date_filed", nullable = false)
    private LocalDate dateFiled;

    @Column(name = "date_resolved")
    private LocalDate dateResolved;
}



