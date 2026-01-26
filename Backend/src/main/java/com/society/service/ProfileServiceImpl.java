package com.society.service;

import com.society.dto.ProfileResponseDTO;
import com.society.dto.ProfileUpdateDTO;
import com.society.entity.User;
import com.society.repository.UserRepository;
import com.society.util.LoggedInUserUtil;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final LoggedInUserUtil loggedInUserUtil;

    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDTO getProfile(HttpSession session) {

        User user = loggedInUserUtil.getUser(session);
        return mapToDto(user);
    }

    @Override
    @Transactional
    public ProfileResponseDTO updateProfile(
            ProfileUpdateDTO dto,
            HttpSession session) {

        User user = loggedInUserUtil.getUser(session);

        user.setFirstName(dto.getFirstName());
        user.setMiddleName(dto.getMiddleName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());

        User updated = userRepository.save(user);
        return mapToDto(updated);
    }

    private ProfileResponseDTO mapToDto(User user) {
        return new ProfileResponseDTO(
                user.getUserId(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().name(),
                user.getSociety().getSocietyId(),
                user.getFlat() != null ? user.getFlat().getFlatId() : null
        );
    }
}
