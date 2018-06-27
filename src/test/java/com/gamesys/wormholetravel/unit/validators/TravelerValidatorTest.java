package com.gamesys.wormholetravel.unit.validators;

import com.gamesys.wormholetravel.validators.TravelerValidator;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TravelerValidatorTest {

    @Test
    public void shouldBeValidPgi() {
        final String pgi = "a1s2d3f4g0";

        final Map<String, String> errors = new TravelerValidator().validatePgi(pgi);

        assertTrue(errors.isEmpty());
    }

    @Test
    public void shouldValidateLargerPgi() {
        final String pgi = "asdfg12345xx";

        final Map<String, String> errors = new TravelerValidator().validatePgi(pgi);

        assertTrue(errors.containsKey(TravelerValidator.MSG.InvalidPgi.KEY));
    }

    @Test
    public void shouldValidateSmallerPgi() {
        final String pgi = "asd";

        final Map<String, String> errors = new TravelerValidator().validatePgi(pgi);

        assertTrue(errors.containsKey(TravelerValidator.MSG.InvalidPgi.KEY));
    }

    @Test
    public void shouldValidateInvalidFirstPositionPgi() {
        final String pgi = "3asd123";

        final Map<String, String> errors = new TravelerValidator().validatePgi(pgi);

        assertTrue(errors.containsKey(TravelerValidator.MSG.InvalidPgi.KEY));
    }

}
