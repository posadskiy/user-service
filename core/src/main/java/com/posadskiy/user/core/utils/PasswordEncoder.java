package com.posadskiy.user.core.utils;

import jakarta.inject.Singleton;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Singleton
public class PasswordEncoder {
    public static final int PASSWORD_STRENGTH = 16;
    public static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(PASSWORD_STRENGTH);

    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}
