package com.gamesys.wormholetravel.unit.services;

import com.gamesys.wormholetravel.repositories.TravelRepository;
import com.gamesys.wormholetravel.services.DefaultTravelService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class DefaultTravelServiceTest {

    @InjectMocks
    private DefaultTravelService service;

    @Mock
    private TravelRepository repository;

    @Test
    public void shouldCreateTravel() {

    }

}
