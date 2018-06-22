package com.gamesys.wormholetravel.services;

import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.TravelDetail;
import org.springframework.stereotype.Service;

@Service
public class DefaultTravelService implements TravelService {

    @Override
    public ServiceResponse travel(final TravelDetail travelDetail) {
        return null;
    }
}
