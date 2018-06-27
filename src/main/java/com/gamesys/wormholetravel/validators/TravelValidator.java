package com.gamesys.wormholetravel.validators;

import com.gamesys.wormholetravel.models.Travel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TravelValidator {

    public static final class MSG {
        public final class SameSpacetime {
            public  static final String KEY = "error.destination";
            public  static final String MSG = "You already in this spacetime";
        }
        public final class BlankPlace {
            public  static final String KEY = "error.destination.place";
            public  static final String MSG = "Place is required";
        }
        public final class NullDate {
            public  static final String KEY = "error.destination.date";
            public  static final String MSG = "Date is required";
        }
    }

    public Map<String, String> validateRequired(final Travel destination) {
        final Map<String,String> errors = new HashMap<>();

        if (StringUtils.isBlank(destination.getPlace())) {
            errors.put(MSG.BlankPlace.KEY, MSG.BlankPlace.MSG);
        }

        if (destination.getDate() == null) {
            errors.put(MSG.NullDate.KEY, MSG.NullDate.MSG);
        }

        return errors;
    }

    public Map<String, String> validateTravel(final Travel current, final Travel destination) {
        final Map<String,String> errors = validateRequired(destination);

        if (errors.isEmpty() && isSameSpacetime(current, destination)) {
            errors.put(MSG.SameSpacetime.KEY, MSG.SameSpacetime.MSG);
        }

        return errors;
    }

    private boolean isSameSpacetime(Travel current, Travel destination) {
        return current.getDate().longValue() == destination.getDate()
                && current.getPlace().equals(destination.getPlace());
    }
}
