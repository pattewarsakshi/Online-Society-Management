package com.society.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitorEntryRequestDto {

    @NotBlank
    private String visitorName;

    @NotBlank
    private String visitorPhone;

    @NotBlank
    private String purpose;

    private Long propertyId; // optional (guest / delivery)
}
