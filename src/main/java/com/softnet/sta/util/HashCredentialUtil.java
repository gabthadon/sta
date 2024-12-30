package com.softnet.sta.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashCredentialUtil {
    // Method to hash the password using SHA-256
    public static String hashing(String credential) {
        return Hashing.sha256()
                .hashString(credential, StandardCharsets.UTF_8)
                .toString();
    }
}
