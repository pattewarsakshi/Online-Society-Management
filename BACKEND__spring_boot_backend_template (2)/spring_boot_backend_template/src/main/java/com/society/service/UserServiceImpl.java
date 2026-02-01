package com.society.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.dto.LoginRequestDTO;
import com.society.dto.LoginResponseDTO;
import com.society.dto.RegisterRequestDTO;
import com.society.entity.Flat;
import com.society.entity.Society;
import com.society.entity.User;
import com.society.entityenum.Role;
import com.society.entityenum.FlatStatus;
import com.society.repository.FlatRepository;
import com.society.repository.SocietyRepository;
import com.society.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SocietyRepository societyRepository;
    private final FlatRepository flatRepository;
    private final PasswordEncoder passwordEncoder;

    // ================= REGISTER =================
    @Override
    public User registerUser(RegisterRequestDTO dto) {

        // ✅ Email uniqueness check
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // ✅ Society validation
        Society society = societyRepository.findById(dto.getSocietyId())
                .orElseThrow(() -> new RuntimeException("Society not found"));

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setMiddleName(dto.getMiddleName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        // 🔐 BCrypt password hashing
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // ✅ Reject if someone tries to set ADMIN manually
        if (dto.getRole() == Role.ADMIN) {
            throw new RuntimeException("Cannot register as ADMIN");
        }

        user.setRole(dto.getRole());
        user.setSociety(society);

        // ✅ No flat assignment at registration
        // Flats will be assigned later by admin

        return userRepository.save(user);
    }

    // ================= LOGIN =================
    @Override
    public LoginResponseDTO loginUser(
            LoginRequestDTO request,
            HttpSession session) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Check if user is approved (has a flat assigned if RESIDENT)
        if (user.getRole() == Role.RESIDENT && user.getFlat() == null) {
            throw new RuntimeException("Your account is pending admin approval. Please wait until a flat is assigned.");
        }
        
        // ✅ store logged-in user in session
        session.setAttribute("LOGGED_USER_ID", user.getUserId());

        // So That Admin can sign in even without flatId
        Integer flatId = null;
        if (user.getFlat() != null) {
            flatId = user.getFlat().getFlatId();
        }
        
        // ✅ FULL RESPONSE (Postman-friendly)
        return new LoginResponseDTO(
                user.getUserId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getRole().name(),
                user.getSociety().getSocietyId(),
                flatId,
                user.getEmail(),          
                user.getPhone()
        );
        
    }

	@Override
	public LoginResponseDTO getUserById(Integer userId) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    return new LoginResponseDTO(
	            user.getUserId(),
	            user.getFirstName() + " " + user.getLastName(),
	            user.getRole().name(),
	            user.getSociety().getSocietyId(),
	            user.getFlat().getFlatId(),  
	            user.getEmail(),                
	            user.getPhone()
	    );
	}

	@Override
	public User getLoggedInUser(HttpSession session) {
	    Integer userId = (Integer) session.getAttribute("LOGGED_USER_ID");

	    if (userId == null) {
	        throw new RuntimeException("User not logged in");
	    }

	    return userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
	}
}
