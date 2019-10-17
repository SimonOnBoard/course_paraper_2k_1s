package service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncripter {
    public static String getPass(String password){
        byte[] data1 = new byte[0];
        try {
            data1 = password.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            new IllegalArgumentException("Что-то сильно пошло не так");
        }

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            new IllegalArgumentException("Программист просто криворукий или библиотека((");
        }
        byte[] digest = messageDigest.digest(data1);
        StringBuilder builder = new StringBuilder();
        for(byte b : digest){
            builder.append((char) b);
        }

        return builder.toString();
    }
}
