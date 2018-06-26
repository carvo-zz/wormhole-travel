package com.gamesys.wormholetravel.factories;

import com.gamesys.wormholetravel.commons.DefaultServiceResponse;
import com.gamesys.wormholetravel.commons.ServiceResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultServiceResponseFactory {

    public ServiceResponse createBlank(){
        return new DefaultServiceResponse<>();
    }

    public ServiceResponse createWithErrors(final Map<String, String> errors){
        return new DefaultServiceResponse<>(errors);
    }

    public ServiceResponse createWithError(final String key, final String msg){
        final Map<String, String> errors = new HashMap(){{
            put(key, msg);
        }};
        return new DefaultServiceResponse<>(errors);
    }

    public <T> ServiceResponse<T> createWithResponse(T response){
        return new DefaultServiceResponse<>(response);
    }
}
