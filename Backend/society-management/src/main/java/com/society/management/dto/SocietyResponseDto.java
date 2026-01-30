package com.society.management.dto;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO used to send society details to client.
 * Avoids exposing internal entity structure.
 */
@Getter
@Builder
public class SocietyResponseDto {

    private Long societyId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;
}
