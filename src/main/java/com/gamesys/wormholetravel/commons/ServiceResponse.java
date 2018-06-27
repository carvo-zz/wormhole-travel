package com.gamesys.wormholetravel.commons;

import java.util.Map;

public interface ServiceResponse<T> {

    T getResponse();

    boolean hasError();

    Map<String, String> getErrors();

    String getErrorsAsJson();

}
