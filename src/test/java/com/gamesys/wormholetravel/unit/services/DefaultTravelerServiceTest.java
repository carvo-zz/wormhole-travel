package com.gamesys.wormholetravel.unit.services;

import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.factories.DefaultServiceResponseFactory;
import com.gamesys.wormholetravel.models.Travel;
import com.gamesys.wormholetravel.models.Traveler;
import com.gamesys.wormholetravel.repositories.TravelerRepository;
import com.gamesys.wormholetravel.services.DefaultTravelerService;
import com.gamesys.wormholetravel.validators.TravelValidator;
import com.gamesys.wormholetravel.validators.TravelerValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DefaultTravelerServiceTest {

    @InjectMocks
    private DefaultTravelerService service;

    @Mock
    private TravelerRepository repository;

    @Mock
    private TravelValidator travelValidator;

    @Mock
    private TravelerValidator travelerValidator;

    @Spy
    private DefaultServiceResponseFactory responseFactory;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAll() {
        final List<Traveler> travelers = new ArrayList(){{
            add(new Traveler());
            add(new Traveler());
        }};

        when(repository.findAll()).thenReturn(travelers);

        final ServiceResponse<List<Traveler>> r = service.findAll();

        assertFalse(r.hasError());
        assertSame(travelers, r.getResponse());
    }

    @Test
    public void shouldTravelFirstTime() {
        final String pgi = "carvo123";

        when(travelerValidator.validatePgi(pgi)).thenReturn(Collections.emptyMap());

        final ServiceResponse response = service.travel(pgi, new Travel());

        assertFalse(response.hasError());
        verify(repository, times(1)).findByPgi(pgi);
        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldTravel() {
        final String pgi = "carvo123";
        final Travel destination = new Travel();
        final Traveler traveler = mock(Traveler.class);
        final Travel currentTravel = new Travel();

        when(travelerValidator.validatePgi(pgi)).thenReturn(Collections.emptyMap());
        when(repository.findByPgi(pgi)).thenReturn(traveler);
        when(traveler.getCurrentTravel()).thenReturn(currentTravel);
        when(travelValidator.validateTravel(currentTravel, destination)).thenReturn(Collections.emptyMap());

        final ServiceResponse response = service.travel(pgi, destination);

        assertFalse(response.hasError());

        verify(traveler, times(1)).getCurrentTravel();
        verify(traveler, times(1)).travelTo(destination);

        verify(repository, times(1)).findByPgi(pgi);
        verify(repository, times(1)).save(traveler);
        verify(travelValidator, times(1)).validateTravel(currentTravel, destination);
    }

    @Test
    public void shouldValidateInvalidPgi() {
        final String pgi = "asdfg12345xx";

        when(travelerValidator.validatePgi(pgi)).thenReturn(new HashMap<String, String>(){{
            put(TravelerValidator.MSG.InvalidPgi.KEY, TravelerValidator.MSG.InvalidPgi.MSG);
        }});

        final ServiceResponse<Traveler> response = service.travel(pgi, new Travel());

        assertTrue(response.hasError());
        assertEquals(1, response.getErrors().size());
        verify(repository, never()).findByPgi(pgi);
        verify(repository, never()).save(any());
    }

    @Test
    public void shouldValidateInvalidTravel() {
        final String pgi = "asdfg12345";
        final Travel travel = new Travel();

        when(travelerValidator.validatePgi(pgi)).thenReturn(Collections.emptyMap());
        when(repository.findByPgi(pgi)).thenReturn(null);
        when(travelValidator.validateRequired(travel)).thenReturn(new HashMap<String, String>() {{
            put(TravelValidator.MSG.BlankPlace.KEY, TravelValidator.MSG.BlankPlace.MSG);
            put(TravelValidator.MSG.NullDate.KEY, TravelValidator.MSG.NullDate.MSG);
        }});

        final ServiceResponse<Traveler> response = service.travel(pgi, travel);

        assertTrue(response.hasError());
        assertEquals(2, response.getErrors().size());
        verify(repository, times(1)).findByPgi(pgi);
        verify(repository, never()).save(any());
    }

    @Test
    public void shouldFindHistoric() {
        final String pgi = "myPgi";
        final Traveler traveler = new Traveler();
        traveler.setPgi(pgi);
        traveler.setHistoric(new ArrayList(){{ add(new Travel("Where?", 1L)); }});

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
