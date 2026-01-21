package com.society.repository;



/* ========= SPRING DATA JPA ========= */
import org.springframework.data.jpa.repository.JpaRepository;

/* ========= JAVA ========= */
import java.util.Optional;

/* ========= ENTITY ========= */
import com.society.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Find user by email (used in login & register)
    Optional<User> findByEmail(String email);
}


