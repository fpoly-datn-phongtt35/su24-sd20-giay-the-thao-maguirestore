package com.datn.maguirestore.config;

/**
 * @author nguyenkhanhhoa
 *
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

    public static class ORDER_STATUS {

        public static final Integer PENDING = 0;
        public static final Integer WAIT_DELIVERY = 1;
        public static final Integer SHIPPING = 2;
        public static final Integer SUCCESS = 3;
        public static final Integer CANCELED = -1;
        public static final Integer PENDING_CHECKOUT = 4;
    }

    public static class PAYMENT_METHOD {

        public static final Integer CASH = 1;
        public static final Integer CREDIT = 2;
    }

    public static class ORDER_RETURN {

        public static final Integer PENDING = 1;
        public static final Integer PROCESSING = 2;
        public static final Integer FINISH = 3;
        public static final Integer CANCEL = 4;
    }

    public static class PAYMENT_STATUS {

        public static final Integer DONE = 1;
        public static final Integer NONE = 2;
    }

    public static class PAID_METHOD {

        public static final Integer OFF = 1;
        public static final Integer ON = 2;
    }

    public static class DISCOUNT_METHOD {

        public static final Integer TOTAL_MONEY = 1;
        public static final Integer TOTAL_PERCENT = 2;
        public static final Integer PER_MONEY = 3;
        public static final Integer PER_PERCENT = 4;
    }

    private Constants() {}

}
