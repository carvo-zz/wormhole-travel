package com.gamesys.wormholetravel.unit.handlers;

import com.gamesys.wormholetravel.UrlMapping;
import com.gamesys.wormholetravel.commons.ServiceResponse;
import com.gamesys.wormholetravel.handlers.TravelerHandler;
import com.gamesys.wormholetravel.models.Travel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesys.wormholetravel.models.Traveler;
import com.gamesys.wormholetravel.services.TravelerService;
import com.gamesys.wormholetravel.utils.JsonParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TravelerHandler.class)
public class TravelerHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelerService service;

    @Test
    public void shouldRetrieveOldTravels() throws Exception {
        final String expectedBody = "[{\"place\":\"Somewhere 1\",\"date\":1}]";
        final ServiceResponse mockedServiceResponse = mock(ServiceResponse.class);

        when(service.findHistoric(anyString())).thenReturn(mockedServiceResponse);
        when(mockedServiceResponse.hasError()).thenReturn(Boolean.FALSE);
        when(mockedServiceResponse.getResponse()).thenReturn(List.of(new Travel("Somewhere 1", 1L)));

        mockMvc.perform(get(UrlMapping.Travelers.OLD_TRAVELS, "carvo123"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().string(expectedBody))
        ;

        verify(service, times(1)).findHistoric(anyString());
        verify(mockedServiceResponse, times(1)).hasError();
        verify(mockedServiceResponse, never()).getErrorsAsJson();
        verify(mockedServiceResponse, times(1)).getResponse();
    }

    @Test
    public void shouldTravel() throws Exception {
        final Travel travel = new Travel("The Restaurant at the End of the Universe", 0L);

        final ServiceResponse mockedServiceResponse = mock(ServiceResponse.class);

        when(service.travel(anyString(), any())).thenReturn(mockedServiceResponse);
        when(mockedServiceResponse.hasError()).thenReturn(Boolean.FALSE);

        final MockHttpServletRequestBuilder builder = post(UrlMapping.Travelers.TRAVELS, "carvo123")
                .content(JsonParser.toJson(travel))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().string(""))
        ;

        verify(service, times(1)).travel(anyString(), any());
        verify(mockedServiceResponse, times(1)).hasError();
        verify(mockedServiceResponse, never()).getErrorsAsJson();
        verify(mockedServiceResponse, never()).getResponse();
    }

}
