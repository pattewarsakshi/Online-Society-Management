package com.society.entity;

/* ===== JPA / HIBERNATE ===== */
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

/* ===== LOMBOK ===== */
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/* ===== JACKSON (for JSON infinite loop issue) ===== */
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.society.entityenum.FlatStatus;

/* ===== JAVA ===== */
import java.util.List;

@Entity
@Table(name = "flat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Integer propertyId;

    @Column(name = "flat_number", length = 10)
    private String flatNumber;

    @Column(name = "block_type", length = 10)
    private String blockType;

    @Column(name = "area_sqft")
    private Double areaSqft;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlatStatus status;

    // Admin who manages this flat
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managed_by_user_id")
    private User managedBy;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Maintenance> maintenances;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Complaint> complaints;

//    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
//    private List<Booking> bookings;
}
