package com.society.controller;



/* ========= SPRING ========= */
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* ========= DTO ========= */
import com.society.dto.RegisterRequestDTO;

/* ========= SERVICE ========= */
import com.society.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // REGISTER USER
    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequestDTO dto) {
        userService.registerUser(dto);
        return "User registered successfully";
    }
}

