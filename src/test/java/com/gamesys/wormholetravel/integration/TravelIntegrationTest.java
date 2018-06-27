package com.gamesys.wormholetravel.integration;

import com.gamesys.wormholetravel.App;
import com.gamesys.wormholetravel.UrlMapping;
import com.gamesys.wormholetravel.models.Travel;
import com.gamesys.wormholetravel.integration.utils.UrlResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TravelIntegrationTest {

    private static final int STATUS_CREATED = 201;

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate;
    private final HttpHeaders headers;

    public TravelIntegrationTest() {
        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
    }

    @Test @Ignore
    public void shouldTravel() throws Exception {
        final Travel travel = new Travel();
        travel.setDate(0L);
        travel.setPlace("The Restaurant at the End of the Universe");

        final String body = new ObjectMapper().writeValueAsString(travel);
        final HttpEntity<String> entity = new HttpEntity<>(body, headers);
        final String uri = UrlResolver.targetUri(port, UrlMapping.Travelers.TRAVELS);

        final ResponseEntity<String> response = restTemplate.exchange(
                uri.replace("{pgi}", "carvolindo"),
                HttpMethod.POST, entity, String.class);

        Assert.assertEquals(STATUS_CREATED, response.getStatusCodeValue());
    }

}
