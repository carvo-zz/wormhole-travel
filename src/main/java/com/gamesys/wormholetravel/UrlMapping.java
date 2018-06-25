package com.gamesys.wormholetravel;

public final class UrlMapping {
    private static final String BASE = "/wormhole-travel";

    public final class Travelers {
        public static final String BASE = UrlMapping.BASE + "/travelers";

        public static final String TRAVELS = BASE + "/{pgi}/travels";
        public static final String OLD_TRAVELS = BASE + "/{pgi}/old-travels";
    }
}
