package com.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.society.entity.User;
import com.society.entityenum.Role;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Used in login
    Optional<User> findByEmail(String email);

    // Used in registration (BEST PRACTICE)
    boolean existsByEmail(String email);
    
    Optional<User> findByRole(Role role);
}
