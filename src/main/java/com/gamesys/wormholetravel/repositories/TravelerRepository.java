package com.gamesys.wormholetravel.repositories;

import com.gamesys.wormholetravel.models.TravelDetail;

public interface TravelerRepository {
    void addTravel(String pgi, TravelDetail travel);
}
