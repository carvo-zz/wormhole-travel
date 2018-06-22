package com.gamesys.wormholetravel.unit.handlers;

import com.gamesys.wormholetravel.UrlMapping;
import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.models.TravelDetail;
import com.gamesys.wormholetravel.handlers.TravelHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesys.wormholetravel.services.TravelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TravelHandler.class)
public class TravelHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelService service;

    private ObjectMapper jacksonMapper = new ObjectMapper();

    @Test
    public void shouldTravel() throws Exception {
        final TravelDetail travel = new TravelDetail();
        travel.setDate(0L);
        travel.setPersonalGalacticIdentifier("carvo123");
        travel.setPlace("The Restaurant at the End of the Universe");

        final ServiceResponse mockedServiceResponse = mock(ServiceResponse.class);

        when(service.travel(any())).thenReturn(mockedServiceResponse);
        when(mockedServiceResponse.hasError()).thenReturn(false);

        final MockHttpServletRequestBuilder builder = post(UrlMapping.Travels.POST)
                .content(jacksonMapper.writeValueAsString(travel))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().is(201))
                .andExpect(content().string(""))
        ;

        verify(service, times(1)).travel(any());
        verify(mockedServiceResponse, times(1)).hasError();
    }

    @Test
    public void shouldRetrievePgiTravels() {

    }

}
