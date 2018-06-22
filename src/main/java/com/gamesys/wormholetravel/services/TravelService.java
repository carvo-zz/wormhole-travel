package com.gamesys.wormholetravel.services;

import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.TravelDetail;

public interface TravelService {
    ServiceResponse travel(TravelDetail travelDetail);
}
