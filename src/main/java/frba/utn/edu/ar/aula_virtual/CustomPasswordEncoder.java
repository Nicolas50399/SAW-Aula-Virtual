package frba.utn.edu.ar.aula_virtual;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class CustomPasswordEncoder implements PasswordEncoder {

    @Value("${app.security.salt}")
    private String SALT;

    @Value("${app.security.hash-algorithm}")
    private String HASH_ALGORITHM;

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(SALT.getBytes(StandardCharsets.UTF_8)); // salt antes de la password
            byte[] hashed = md.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder(hashed.length * 2);
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}

