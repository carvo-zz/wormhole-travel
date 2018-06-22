package com.gamesys.wormholetravel.services;

import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.TravelDetail;
import com.gamesys.wormholetravel.models.Traveler;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface TravelService {
    ServiceResponse travel(@NotNull Traveler traveler);
}
