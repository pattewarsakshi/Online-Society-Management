package com.society.management.dto;

import com.society.management.enumtype.Role;
import lombok.*;

/**
 * Output DTO (never expose password)
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private Long societyId;
}
