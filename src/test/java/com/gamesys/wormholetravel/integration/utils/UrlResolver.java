package com.gamesys.wormholetravel.integration.utils;

public final class UrlResolver {

    private UrlResolver() { }

    /**
     * Creates the target URI to test
     *
     * @param port app port
     * @param targetResource should <b>start with slash</b>
     * @return localhost:<code>port</code>/<code>targetResource</code>
     */
    public static String targetUri(final int port, final String targetResource) {
        return "http://localhost:" + port + targetResource;
    }
}
