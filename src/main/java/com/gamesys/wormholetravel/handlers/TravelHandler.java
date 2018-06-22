package com.gamesys.wormholetravel.handlers;

import com.gamesys.wormholetravel.UrlMapping;
import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.TravelDetail;
import com.gamesys.wormholetravel.services.TravelService;
import com.gamesys.wormholetravel.utils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TravelHandler {

    @Autowired
    private TravelService service;

    @PostMapping(UrlMapping.Travels.POST)
    public ResponseEntity<String> post(final TravelDetail travelDetail) {
        final ServiceResponse response = service.travel(travelDetail);

        String responseBody = "";
        if (response.hasError()) {
            responseBody = JsonParser.toJson(response.getErrors());
        }

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

}
