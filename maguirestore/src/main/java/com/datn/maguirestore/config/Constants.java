package com.datn.maguirestore.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    public static class STATUS {

        public static final Integer ACTIVE = 1;
        public static final Integer IN_ACTIVE = 0;
        public static final Integer DELETE = -1;
    }

    public static class DISCOUNT_METHOD {

        public static final Integer TOTAL_MONEY = 1;
        public static final Integer TOTAL_PERCENT = 2;
        public static final Integer PER_MONEY = 3;
        public static final Integer PER_PERCENT = 4;
    }

    private Constants() {}

    public static final String KEY_UPLOAD = "bad";
}
