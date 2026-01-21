


package com.society.entity;
import java.time.LocalDate;
import java.util.List;

import com.society.entityenum.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(nullable = false)
	private String firstName;
	
	private String middleName;
	
	@Column(nullable = false)
    private String lastName;
	
	@Column(unique = true, nullable = false)
    private String email;
	
	 private String phone;
	 
	 @Column(nullable = false)
	    private String password;
	 
	 @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private Role role;
	 
	 private LocalDate registrationDate;
	 
	 @ManyToOne
	    @JoinColumn(name = "property_id")
	    private Flat flat;
	 
//	 @OneToMany(mappedBy = "postedBy", fetch = FetchType.LAZY)
//	    private List<Notice> notices;
//	 
//	 @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//	    private List<Complaint> complaints;
//	
//	    @OneToMany(mappedBy = "user")
//	    private List<Booking> bookings;

}
