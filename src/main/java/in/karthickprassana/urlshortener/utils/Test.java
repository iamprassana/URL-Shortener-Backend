package in.karthickprassana.urlshortener.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Test {

    @Value("${USER_NAME:NOT_FOUND}")
    private String userName;

    @Value("${PASSWORD:NOT_FOUND}")
    private String password;

    @PostConstruct
    public void print() {
//        System.out.println("USER_NAME = " + userName);
//        System.out.println("PASSWORD = " + password);
    }
}