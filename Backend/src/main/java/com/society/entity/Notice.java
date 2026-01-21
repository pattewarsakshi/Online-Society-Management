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

/* ========= LOMBOK ========= */
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/* ========= JACKSON ========= */
import com.fasterxml.jackson.annotation.JsonBackReference;

/* ========= JAVA ========= */
import java.time.LocalDate;
import com.society.entity.User;

@Entity
@Table(name = "notice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Integer noticeId;

    // ADMIN user who posted the notice
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User postedBy;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "date_posted", nullable = false)
    private LocalDate datePosted;

    @Column(name = "email_sent")
    private Boolean emailSent;
}
