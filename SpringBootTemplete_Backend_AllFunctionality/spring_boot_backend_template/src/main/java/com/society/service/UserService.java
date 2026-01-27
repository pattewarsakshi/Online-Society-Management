package com.society.service;

import com.society.dto.RegisterRequestDTO;
import com.society.dto.LoginRequestDTO;
import com.society.dto.LoginResponseDTO;
import com.society.entity.User;

import jakarta.servlet.http.HttpSession;

public interface UserService {

    User registerUser(RegisterRequestDTO request);

    // LOGIN
    LoginResponseDTO loginUser(LoginRequestDTO request, HttpSession session);
}
