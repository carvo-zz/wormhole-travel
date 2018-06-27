package com.gamesys.wormholetravel.integration;

import com.gamesys.wormholetravel.App;
import com.gamesys.wormholetravel.UrlMapping;
import com.gamesys.wormholetravel.models.Travel;
import com.gamesys.wormholetravel.integration.utils.UrlResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesys.wormholetravel.models.Traveler;
import com.gamesys.wormholetravel.repositories.TravelerRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TravelIntegrationTest {

    @Autowired
    private TravelerRepository repo;

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate;
    private final HttpHeaders headers;

    public TravelIntegrationTest() {
        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    @Test
    public void shouldTravel() throws Exception {
        final String pgi = "carvolindo";
        final String place = "The Restaurant at the End of the Universe";
        final long date = 0L;

        final Travel travel = new Travel();
        travel.setDate(date);
        travel.setPlace(place);

        final String body = new ObjectMapper().writeValueAsString(travel);
        final HttpEntity<String> entity = new HttpEntity<>(body, headers);
        final String uri = UrlResolver.targetUri(port, UrlMapping.Travelers.TRAVELS)
                .replace("{pgi}", pgi);

        final ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, entity, String.class);

        final Traveler traveler = repo.findByPgi(pgi);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());

        assertEquals(date, traveler.getCurrentTravel().getDate().longValue());
        assertEquals(place, traveler.getCurrentTravel().getPlace());
    }

    @Test
    public void shouldValidatePgi() throws Exception {
        final String expected = "{\"error.invalidPgi\":" +
                "\"Invalid PGI format: should be alphanumeric, always starts with a letter, between 5-10 characters\"}";

        final String pgi = "111carvoDoisS";

        final String body = new ObjectMapper().writeValueAsString(new Travel("somewhere", 3L));
        final HttpEntity<String> entity = new HttpEntity<>(body, headers);
        final String uri = UrlResolver.targetUri(port, UrlMapping.Travelers.TRAVELS)
                .replace("{pgi}", pgi);

        final ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, entity, String.class);

        final Traveler traveler = repo.findByPgi(pgi);

        assertNull(traveler);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        JSONAssert.assertEquals(expected, response.getBody(), Boolean.FALSE);
    }

    @Test
    public void shouldValidateTravelDateAndPlace() throws Exception {
        final String expected = "{\"error.destination.place\":\"Place is required\"," +
                "\"error.destination.date\":\"Date is required\"}";

        final String pgi = "carvo123";

        final String body = new ObjectMapper().writeValueAsString(new Travel());
        final HttpEntity<String> entity = new HttpEntity<>(body, headers);
        final String uri = UrlResolver.targetUri(port, UrlMapping.Travelers.TRAVELS)
                .replace("{pgi}", pgi);

        final ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, entity, String.class);

        final Traveler traveler = repo.findByPgi(pgi);

        assertNull(traveler);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        JSONAssert.assertEquals(expected, response.getBody(), Boolean.FALSE);
    }
}
