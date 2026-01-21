package com.society.service;



/* ========= SPRING ========= */
import org.springframework.stereotype.Service;

/* ========= JAVA ========= */
import java.time.LocalDate;

/* ========= DTO ========= */
import com.society.dto.RegisterRequestDTO;

/* ========= ENTITY ========= */
import com.society.entity.User;

/* ========= REPOSITORY ========= */
import com.society.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(RegisterRequestDTO dto) {

        // Check duplicate email
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // DTO → Entity
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // plain password (temporary)
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setRegistrationDate(LocalDate.now());

        // Save user
        userRepository.save(user);
        
        
    }
    /* ========= ENTITY ========= */
  

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}

