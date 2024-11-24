package com.bwslc.locationsuggestion.applayer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwslc.locationsuggestion.applayer.service.SuggestionService;
import com.bwslc.locationsuggestion.dto.LocationSuggestionDTO;
import com.bwslc.locationsuggestion.dto.SuccessResponse;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suggestions")
public class SuggestionController {

    private final SuggestionService suggestionServiceInstance;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<LocationSuggestionDTO>>> getSuggestionLocation(@RequestParam String q,
            @RequestParam(required = false) BigDecimal latitude,
            @RequestParam(required = false) BigDecimal longitude) {
        return suggestionServiceInstance.getSuggestionLocation(q, latitude, longitude);
    }

}
