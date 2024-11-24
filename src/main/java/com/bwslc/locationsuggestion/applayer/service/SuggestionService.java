package com.bwslc.locationsuggestion.applayer.service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bwslc.locationsuggestion.applayer.repository.LocationRepository;
import com.bwslc.locationsuggestion.dto.LocationSuggestionDTO;
import com.bwslc.locationsuggestion.dto.SuccessResponse;
import com.bwslc.locationsuggestion.mapper.LocationMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SuggestionService {

    private final LocationRepository locationRespository;
    private final LocationMapper locationMapper;

    private float calculateFinalScore(String cityName, String query, BigDecimal cityLat, BigDecimal cityLong,
            BigDecimal userLat, BigDecimal userLong, long population) {
        final double TEXT_WEIGHT = 0.6;
        final double DISTANCE_WEIGHT = 0.3;
        final double POPULATION_WEIGHT = 0.1;

        double textSimilarity = calculateTextSimilarity(cityName, query);
        double populationFactor = calculatePopulationFactor(population);

        if (userLat == null || userLong == null) {
            double score = ((TEXT_WEIGHT + DISTANCE_WEIGHT) * textSimilarity) + (POPULATION_WEIGHT * populationFactor);
            return (float) (Math.round(score * 10.0) / 10.0);
        }

        double distance = calculateDistance(cityLat.doubleValue(), cityLong.doubleValue(), userLat.doubleValue(),
                userLong.doubleValue());

        double finalScore = (TEXT_WEIGHT * textSimilarity) +
                (DISTANCE_WEIGHT * (1.0 - distance / 10000)) +
                (POPULATION_WEIGHT * populationFactor);

        return (float) (Math.round(finalScore * 10.0) / 10.0);
    }

    private double calculateTextSimilarity(String text1, String text2) {
        text1 = text1.toLowerCase();
        text2 = text2.toLowerCase();

        double similarity = (double) (text1.length() - text1.replace(text2, "").length()) / text1.length();
        return similarity;
    }

    private double calculateDistance(double cityLat, double cityLong, double userLat, double userLong) {
        final int EARTH_RADIUS = 6371;

        double latDistance = Math.toRadians(cityLat - userLat);
        double lonDistance = Math.toRadians(cityLong - userLong);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(cityLat)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS * c;
        return distance;
    }

    private double calculatePopulationFactor(long population) {
        long maxPopulation = 10000000;
        return Math.log(population + 1) / Math.log(maxPopulation + 1);
    }

    public ResponseEntity<SuccessResponse<List<LocationSuggestionDTO>>> getSuggestionLocation(String param,
            BigDecimal latitude,
            BigDecimal longitude) {
        if (param.isBlank()) {
            throw new InvalidParameterException("'q' parameter cannot be null");
        }
        List<LocationSuggestionDTO> data = locationRespository.findLocationByNameContaining(param).stream()
                .map(location -> locationMapper.mapLocationToSuggestionLocationDTO(location,
                        calculateFinalScore(location.getName(), param, location.getLatitude(),
                                location.getLongitude(), latitude, longitude,
                                location.getPopulation().longValue())))
                .sorted(Comparator.comparingDouble(LocationSuggestionDTO::getScore).reversed())
                .collect(Collectors.toList());
        SuccessResponse<List<LocationSuggestionDTO>> bodyData = new SuccessResponse<>(data);
        ResponseEntity<SuccessResponse<List<LocationSuggestionDTO>>> responseEntity = new ResponseEntity<>(
                bodyData, HttpStatus.OK);

        return responseEntity;
    }

}
