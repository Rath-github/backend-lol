package com.ufpr.lol.utils;

import java.security.SecureRandom;

public class GerarSenhas {

    public static String generateRandomPassword() {
        int len = 4;
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static String encryptPassword(String password, byte[] salt) {
        String senhaCriptografada = SecureUtils.getSecurePassword(password, salt);
        return senhaCriptografada;
    }
}
