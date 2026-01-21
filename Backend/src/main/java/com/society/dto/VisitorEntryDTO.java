package com.society.dto;



/* ========= ENUM ========= */
import com.society.entityenum.VisitPurpose;

/* ========= LOMBOK ========= */
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorEntryDTO {

    private String visitorName;
    private String phone;
    private VisitPurpose purpose;
}

