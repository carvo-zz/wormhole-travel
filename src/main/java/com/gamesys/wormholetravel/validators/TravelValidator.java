package com.gamesys.wormholetravel.validators;

import com.gamesys.wormholetravel.models.Travel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TravelValidator {

    public Map<String, String> validateTravel(Travel current, Travel destination) {
        final Map<String,String> errors = new HashMap<>();

        if (isSameSpacetime(current, destination)) {
            errors.put("error.destination", "You already in this spacetime");
        }

        return errors;
    }

    private boolean isSameSpacetime(Travel current, Travel destination) {
        return current.getDate().longValue() == destination.getDate()
                && current.getPlace().equals(destination.getPlace());
    }
}
