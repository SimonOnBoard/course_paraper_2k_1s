package service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StringEncriptor {
    public static synchronized String getEncryptedString(String notEncryptedString){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(notEncryptedString);
        return hash;
    }
}
