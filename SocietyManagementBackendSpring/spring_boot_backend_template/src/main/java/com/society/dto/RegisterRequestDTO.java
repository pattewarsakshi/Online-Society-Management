package com.society.dto;



import lombok.Data;
import com.society.entityenum.Role;

@Data
public class RegisterRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;   // plain password for now
    private String phone;
    private Role role;
}

