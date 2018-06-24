package com.gamesys.wormholetravel.services;

import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.Travel;
import com.gamesys.wormholetravel.models.Traveler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface TravelerService {
    ServiceResponse<List<Traveler>> findAll();

    ServiceResponse<List<Travel>> findHistoric(String pgi);

    ServiceResponse travel(String pgi, Travel travel);
}
