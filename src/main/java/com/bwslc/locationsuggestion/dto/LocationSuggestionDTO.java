package com.bwslc.locationsuggestion.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class LocationSuggestionDTO {
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Float score;
}
