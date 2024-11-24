package com.bwslc.locationsuggestion.applayer.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.util.List;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bwslc.locationsuggestion.applayer.service.SuggestionService;
import com.bwslc.locationsuggestion.dto.LocationSuggestionDTO;
import com.bwslc.locationsuggestion.dto.SuccessResponse;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SuggestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SuggestionService suggestionServiceMockInstance;

    private List<LocationSuggestionDTO> mockSuggestions;

    @BeforeEach
    public void init() {

        LocationSuggestionDTO suggestion1 = new LocationSuggestionDTO("London, 08, CA", new BigDecimal("42.98339"),
                new BigDecimal("-81.23304"), 0.9f);
        LocationSuggestionDTO suggestion2 = new LocationSuggestionDTO("London, KY, US", new BigDecimal("37.12898"),
                new BigDecimal("-84.08326"), 0.8f);
        LocationSuggestionDTO suggestion3 = new LocationSuggestionDTO("London, OH, US", new BigDecimal("39.88645"),
                new BigDecimal("-83.44825"), 0.8f);
        LocationSuggestionDTO suggestion4 = new LocationSuggestionDTO("Londonderry, NH, US", new BigDecimal("42.86509"),
                new BigDecimal("-71.37395"), 0.6f);
        LocationSuggestionDTO suggestion5 = new LocationSuggestionDTO("Londontowne, MD, US", new BigDecimal("38.93345"),
                new BigDecimal("-76.54941"), 0.6f);

        mockSuggestions = List.of(suggestion1, suggestion2, suggestion3, suggestion4, suggestion5);
    }

    @Test
    void testGetSuggestionLocation_withValidParameter() throws Exception {

        when(suggestionServiceMockInstance.getSuggestionLocation("Londo", new BigDecimal("43.70011"),
                new BigDecimal("-79.4163")))
                .thenReturn(new ResponseEntity<>(new SuccessResponse<>(mockSuggestions), HttpStatus.OK));

        mockMvc
                .perform(get("/api/suggestions").param("q", "Londo").param("latitude", "43.70011").param("longitude",
                        "-79.4163"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.suggestions.length()").value(5));
    }

    @Test
    void testGetSuggestionLocation_suggestionNotFound() throws Exception {
        when(suggestionServiceMockInstance.getSuggestionLocation("SomeRandomCityInTheMiddleOfNowhere", null, null))
                .thenReturn(new ResponseEntity<>(new SuccessResponse<>(List.of()), HttpStatus.OK));

        mockMvc.perform(get("/api/suggestions").param("q", "SomeRandomCityInTheMiddleOfNowhere"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.suggestions.length()").value(0));
    }

    @Test
    void testGetSuggestionLocation_withNoValidParameter() throws Exception {
        when(suggestionServiceMockInstance.getSuggestionLocation("", null, null))
                .thenReturn(new ResponseEntity<>(new SuccessResponse<>(List.of()), HttpStatus.BAD_REQUEST));

        mockMvc.perform(get("/api/suggestions")).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
