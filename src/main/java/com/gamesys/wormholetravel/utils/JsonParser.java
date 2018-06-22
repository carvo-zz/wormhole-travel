package com.gamesys.wormholetravel.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonParser {
    private class Msg {
        private static final String JSON_CONV_ERROR = "#toJson(): conversion error";
    }

    private static final Logger log = LoggerFactory.getLogger(JsonParser.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonParser() {
    }

    /**
     *
     * @param o to format
     * @return json format of <code>o</code>
     */
    public static String toJson(final Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (final  JsonProcessingException e) {
            log.error(Msg.JSON_CONV_ERROR, e);
            throw new IllegalArgumentException(Msg.JSON_CONV_ERROR, e);
        }
    }
}
