package com.society.management.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard error response sent to client.
 */
@Getter
@Builder
public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    /**
     * Field-wise validation errors (optional)
     */
    private Map<String, String> errors;
}
