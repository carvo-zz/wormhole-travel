package com.gamesys.wormholetravel.unit.validators;

import com.gamesys.wormholetravel.models.Travel;
import com.gamesys.wormholetravel.validators.TravelValidator;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TravelValidatorTest {

    @Test
    public void shouldBeValidTravel() {
        final Travel current = new Travel("place", 1L);
        final Travel destination = new Travel("place", 2L);

        final Map<String, String> errors = new TravelValidator().validateTravel(current, destination);

        assertTrue(errors.isEmpty());
    }

    @Test
    public void shouldValidateRequiredFields() {
        final Map<String, String> errors = new TravelValidator().validateRequired(new Travel());

        assertTrue(errors.containsKey(TravelValidator.MSG.NullDate.KEY));
        assertTrue(errors.containsKey(TravelValidator.MSG.BlankPlace.KEY));
    }

    @Test
    public void shouldValidateSameLocation() {
        final Travel current = new Travel("place", 1L);
        final Travel destination = new Travel("place", 1L);

        final Map<String, String> errors = new TravelValidator().validateTravel(current, destination);

        assertTrue(errors.containsKey(TravelValidator.MSG.SameSpacetime.KEY));
        assertFalse(errors.containsKey(TravelValidator.MSG.NullDate.KEY));
        assertFalse(errors.containsKey(TravelValidator.MSG.BlankPlace.KEY));
    }

}
