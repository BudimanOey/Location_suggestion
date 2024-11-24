package com.bwslc.locationsuggestion.mapper;

import org.springframework.stereotype.Component;

import com.bwslc.locationsuggestion.dto.LocationSuggestionDTO;
import com.bwslc.locationsuggestion.entity.Location;

@Component
public class LocationMapper {
    
    private final String LOCATION_NAME_FORMAT = "%s, %s, %s";

    public LocationSuggestionDTO mapLocationToSuggestionLocationDTO(Location location, Float score) {
        String result = String.format(LOCATION_NAME_FORMAT, location.getName(), location.getAdmin1(), location.getCountry());
        return new LocationSuggestionDTO(result, location.getLatitude(), location.getLongitude(), score);
    }

}
