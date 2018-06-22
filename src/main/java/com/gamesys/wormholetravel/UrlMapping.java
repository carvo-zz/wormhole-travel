package com.gamesys.wormholetravel;

public final class UrlMapping {
    private static final String BASE = "/time-traveler";


    public final class Travels {
        public static final String POST = BASE + "/travels";
    }

    public final class Travelers {
        private static final String TRAVALERS_BASE = "/travelers/{pgi}";
        public static final String GET_TRAVELS = BASE + TRAVALERS_BASE + "/travels";
    }
}
