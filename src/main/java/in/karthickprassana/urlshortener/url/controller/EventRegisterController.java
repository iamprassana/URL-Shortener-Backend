package in.karthickprassana.urlshortener.url.controller;

import in.karthickprassana.urlshortener.url.service.URLService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class EventRegisterController {

    private final URLService urlService;
    //TODO: Create a Kafka producer class

    @GetMapping("/{url}")
    public ResponseEntity<Void> redirectUser(@PathVariable String url, HttpServletRequest http) {

        String destinationURL = urlService.getOriginalURL(url);
        //Register the event using Kafka

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(destinationURL))
                .build();
    }
}
