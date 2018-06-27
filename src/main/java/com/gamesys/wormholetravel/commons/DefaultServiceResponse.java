package com.gamesys.wormholetravel.commons;

import com.gamesys.wormholetravel.utils.JsonParser;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultServiceResponse<T> implements ServiceResponse {
    private T response;

    private Map<String, String> errors;

    public DefaultServiceResponse() {

    }

    public DefaultServiceResponse(T response) {
        this.response = response;
    }

    public DefaultServiceResponse(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    @Override
    public boolean hasError() {
        return errors != null && !errors.isEmpty();
    }

    @Override
    public Map<String, String> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public String getErrorsAsJson() {
        return JsonParser.toJson(getErrors());
    }

    public void addError(final String key, final String msg) {
        this.errors = Optional.of(this.errors).orElse(new LinkedHashMap<>());
        this.errors.put(key, msg);
    }
}
