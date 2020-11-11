package pl.politechnika.goalreacher.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JWTUtils {

    private static final int TOKEN_DURATION = 15 * 1000 * 1000;
    private static final String DEV_SECRET_512 = "nZr4u7x!A%D*F-JaNdRgUkXp2s5v8y/B?E(H+KbPeShVmYq3t6w9z$C&F)J@NcQf";

    public static String getToken(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_DURATION))
                .sign(Algorithm.HMAC512(getSecret()));
    }

    public static String getSecret() {
        String secureSecret512 = System.getenv("SECRET_512");
        if (secureSecret512 == null) {
            return DEV_SECRET_512;
        }
        return secureSecret512;
    }
}

