package com.gamesys.wormholetravel.services;

import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.TravelDetail;
import com.gamesys.wormholetravel.models.Traveler;
import com.gamesys.wormholetravel.repositories.TravelerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class DefaultTravelService implements TravelService {

    @Autowired
    private TravelerRepository travelerRepository;

    @Override
    public ServiceResponse travel(final Traveler traveler) {
        this.travelerRepository.addTravel(traveler.getPersonalGalacticIdentifier(), traveler.getTravel());
        return new ServiceResponse();
    }
}
