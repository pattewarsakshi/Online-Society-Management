package com.society.management.repository;

import com.society.management.entity.User;
import com.society.management.enumtype.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    boolean existsBySociety_SocietyIdAndRole(Long societyId, Role role);

    boolean existsByEmailOrPhone(String email, String phone);
    
    //===================================================
    @Query("""
    		 SELECT
    		   SUM(CASE WHEN u.role = 'ADMIN' THEN 1 ELSE 0 END),
    		   SUM(CASE WHEN u.role = 'OWNER' THEN 1 ELSE 0 END),
    		   SUM(CASE WHEN u.role = 'TENANT' THEN 1 ELSE 0 END)
    		 FROM User u
    		 WHERE u.society.societyId = :societyId
    		""")
    		Object[] countUsersByRole(@Param("societyId") Long societyId);
 //============================================================
    

}
