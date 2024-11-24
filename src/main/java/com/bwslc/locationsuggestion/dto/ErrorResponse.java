package com.bwslc.locationsuggestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Integer status;
    private String message;
    private String error;
    private String timestamp;
}
