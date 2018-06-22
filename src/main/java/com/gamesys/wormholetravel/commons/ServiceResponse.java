package com.gamesys.wormholetravel.commons;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ServiceResponse<T> {

    private T response;

    private Map<String, String> errors;

    public ServiceResponse() {
        this.errors = new LinkedHashMap<>();
    }

    public ServiceResponse(T response) {
        this.response = response;
        this.errors = new LinkedHashMap<>();
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
