package com.gamesys.wormholetravel.validators;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class TravelerValidator {

    public static final class MSG {
        public final class InvalidPgi {
            public  static final String KEY = "error.invalidPgi";
            public  static final String MSG = "Invalid PGI format: " +
                    "sould be alphanumeric, always starts with a letter, between 5-10 characters";
        }

        public final class PgiNotFound {
            public static final String KEY = "error.pgiNotFound";
            public static final String MSG = "Required pgi not found";
        }
    }

    private static final String PGI_PATTERN = "[A-Za-z][A-Za-z0-9]{4,9}";

    public Map<String, String> validatePgi(final String pgi) {
        final Map<String, String> errors = new HashMap<>();

        if (!isValidPgi(pgi)) {
            errors.put(MSG.InvalidPgi.KEY, MSG.InvalidPgi.MSG);
        }

        return errors;
    }

    private boolean isValidPgi(String pgi) {
        return Optional.ofNullable(pgi)
                .map(gi -> gi.matches(PGI_PATTERN))
                .orElse(Boolean.FALSE);
    }

}
