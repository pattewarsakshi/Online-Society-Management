package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private Integer userId;
    private String name;
    private String role;
    private Integer societyId;
    private Integer flatId;
    private String email;  
    private String phone;
    //private String token;
}
