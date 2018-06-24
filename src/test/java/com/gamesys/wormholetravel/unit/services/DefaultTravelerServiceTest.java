package com.gamesys.wormholetravel.unit.services;

import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.Travel;
import com.gamesys.wormholetravel.models.Traveler;
import com.gamesys.wormholetravel.repositories.TravelerRepository;
import com.gamesys.wormholetravel.services.DefaultTravelerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DefaultTravelerServiceTest {

    @InjectMocks
    private DefaultTravelerService service;

    @Mock
    private TravelerRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAll() {
        final List<Traveler> travelers = List.of(new Traveler(), new Traveler());

        when(repository.findAll()).thenReturn(travelers);

        final ServiceResponse<List<Traveler>> r = service.findAll();

        assertFalse(r.hasError());
        assertSame(travelers, r.getResponse());
    }

    @Test
    public void shouldTravelFirstTime() {
        final String pgi = "carvo123";

        final ServiceResponse response = service.travel(pgi, new Travel());

        assertFalse(response.hasError());
        verify(repository, times(1)).findByPgi(pgi);
        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldTravel() {
        final String pgi = "carvo123";
        final Travel travel = new Travel();
        final Traveler traveler = mock(Traveler.class);

        when(repository.findByPgi(pgi)).thenReturn(traveler);

        final ServiceResponse response = service.travel(pgi, travel);

        assertFalse(response.hasError());
        verify(traveler, times(1)).setCurrentTravel(travel);
        verify(traveler, times(1)).setHistoric(any());
        verify(repository, times(1)).findByPgi(pgi);
        verify(repository, times(1)).save(traveler);
    }

    @Test
    public void shouldFindHistoric() {
        final String pgi = "myPgi";
        final Traveler traveler = new Traveler();
        traveler.setPgi(pgi);
        traveler.setHistoric(List.of(new Travel("Where?", 1L)));

        when(repository.findByPgi(pgi)).thenReturn(traveler);

        final ServiceResponse<List<Travel>> response = service.findHistoric(pgi);

        assertEquals(1, response.getResponse().size());
        assertFalse(response.hasError());
        verify(repository, times(1)).findByPgi(pgi);
    }

    @Test
    public void shouldFindEmptyHistoric (){
        final String pgi = "myPgi";
        final Traveler traveler = mock(Traveler.class);

        when(repository.findByPgi(pgi)).thenReturn(traveler);

        final ServiceResponse<List<Travel>> response = service.findHistoric(pgi);

        assertTrue(response.getResponse().isEmpty());
        assertFalse(response.hasError());
        verify(repository, times(1)).findByPgi(pgi);
        verify(traveler, times(1)).getHistoric();
    }

    @Test
    public void shouldNotFindPgi() {
        final String pgi = "myPgi";

        when(repository.findByPgi(pgi)).thenReturn(null);

        final ServiceResponse<List<Travel>> response = service.findHistoric(pgi);

        assertNull(response.getResponse());
        assertTrue(response.hasError());
        verify(repository, times(1)).findByPgi(pgi);
    }

}
