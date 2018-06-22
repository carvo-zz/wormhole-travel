package com.gamesys.wormholetravel.unit.services;

import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.Traveler;
import com.gamesys.wormholetravel.repositories.TravelerRepository;
import com.gamesys.wormholetravel.services.DefaultTravelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTravelServiceTest {

    @InjectMocks
    private DefaultTravelService service;

    @Mock
    private TravelerRepository repository;

    @Test
    public void shouldCreateTravel() {
        final Traveler traveler = new Traveler();

        final ServiceResponse response = service.travel(traveler);

        assertNotNull(response);
        assertFalse(response.hasError());
        verify(repository, times(1))
                .addTravel(traveler.getPersonalGalacticIdentifier(), traveler.getTravel());
    }

}
