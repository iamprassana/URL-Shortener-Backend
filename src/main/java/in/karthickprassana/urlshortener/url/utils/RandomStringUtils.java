package in.karthickprassana.urlshortener.url.utils;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomStringUtils {

    private static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public RandomStringUtils() {}

    public static String generateString(int n) {

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < n; i++) {
            sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }

        return sb.toString();
    }
}
