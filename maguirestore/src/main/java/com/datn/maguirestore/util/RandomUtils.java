package com.datn.maguirestore.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author nguyenkhanhhoa
 */
public class RandomUtils {

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static String generateResetKey() {
        byte[] randomBytes = new byte[15];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String generateActivationKey() {
        byte[] randomBytes = new byte[15];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
