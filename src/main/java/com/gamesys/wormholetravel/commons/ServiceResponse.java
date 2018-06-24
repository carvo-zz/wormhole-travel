package com.gamesys.wormholetravel.commons;

import com.gamesys.wormholetravel.utils.JsonParser;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ServiceResponse<T> {

    private T response;

    private Map<String, String> errors;

    public ServiceResponse() {
    }

    public ServiceResponse(T response) {
        this.response = response;
    }

    public ServiceResponse(Map<String, String> errors) {
        this.errors = errors;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Map<String, String> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

    public String getErrorsAsJson() {
        return JsonParser.toJson(getErrors());
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public void addError(final String key, final String msg) {
        this.errors = Optional.of(this.errors).orElse(new LinkedHashMap<>());
        this.errors.put(key, msg);
    }

    public boolean hasError() {
        return errors != null && !errors.isEmpty();
    }
}
