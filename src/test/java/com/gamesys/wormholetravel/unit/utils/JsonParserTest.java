package com.gamesys.wormholetravel.unit.utils;

import com.gamesys.wormholetravel.utils.JsonParser;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.HashMap;
import java.util.Map;

public class JsonParserTest {

    @Test
    public void shouldConvertDto2Json() throws JSONException {
        final class SomeDto {
            private int id;
            private String name;
            private SomeDto anotherDto;

            public SomeDto() {
                id = 1;
                name = "Some name";
                anotherDto = new SomeDto(2, "anothername");
            }

            public SomeDto(int id, String name) {
                this.id = id;
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public SomeDto getAnotherDto() {
                return anotherDto;
            }
        }
        final String actual = JsonParser.toJson(new SomeDto());

        final String expected = "{\"id\":1,\"name\":\"Some name\",\"anotherDto\":{" +
                "\"id\":2,\"name\":\"anothername\",\"anotherDto\":null}}";

        JSONAssert.assertEquals(expected, actual, Boolean.FALSE);
    }

    @Test
    public void shouldConvertMap2Json() throws JSONException {
        final Map<String, Object> anotherDto = new HashMap() {{
            put("id", 2);
            put("name", "anothername");
        }};
        final Map<String, Object> data = new HashMap() {{
            put("id", 1);
            put("name", "Some name");
            put("anotherDto", anotherDto);
        }};

        final String actual = JsonParser.toJson(data);

        final String expected = "{\"id\":1,\"name\":\"Some name\",\"anotherDto\":{" +
                "\"id\":2,\"name\":\"anothername\"}}";

        JSONAssert.assertEquals(expected, actual, Boolean.FALSE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForBadUse() {
        final class NotSerializable {
            private final NotSerializable self = this;

            @Override
            public String toString() {
                return self.getClass().getName();
            }
        }
        JsonParser.toJson(new NotSerializable());
    }

}
