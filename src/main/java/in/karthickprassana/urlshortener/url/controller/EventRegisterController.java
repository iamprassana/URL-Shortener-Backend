package in.karthickprassana.urlshortener.url.controller;

import in.karthickprassana.urlshortener.url.dto.ClickEventDTO;
import in.karthickprassana.urlshortener.url.service.URLService;
import in.karthickprassana.urlshortener.url.utils.BrowserType;
import in.karthickprassana.urlshortener.url.utils.DeviceType;
//import in.karthickprassana.urlshortener.utils.GeoIPService;
import in.karthickprassana.urlshortener.utils.KafkaProducer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class EventRegisterController {

    private final URLService urlService;
    private final KafkaProducer producer;
//    private final GeoIPService geoIPService;

    @GetMapping("/{url}")
    public ResponseEntity<Void> redirectUser(
            @PathVariable String url,
            HttpServletRequest http) {

        String destinationURL = urlService.getOriginalURL(url);

        String ip = http.getRemoteAddr();
        String os = http.getHeader("sec-ch-ua-platform");

        String formFactor = http.getHeader("sec-ch-ua-form-factors");
        String browserHeader = http.getHeader("sec-ch-ua");

        Long urlId = urlService.getId(url);

        System.out.println("UA = " + http.getHeader("User-Agent"));
        System.out.println("sec-ch-ua = " + http.getHeader("sec-ch-ua"));
        System.out.println("sec-ch-ua-platform = " + http.getHeader("sec-ch-ua-platform"));
        System.out.println("sec-ch-ua-form-factors = " + http.getHeader("sec-ch-ua-form-factors"));

        ClickEventDTO dto = ClickEventDTO.builder()
                .urlId(urlId)
                .url(url)
                .ip(ip)
                .os(os == null ? "Unknown" : os.replace("\"", ""))
                .deviceType(getDeviceType(formFactor))
                .browserType(getBrowserType(browserHeader))
                .country("Unknown") // Fill this using an IP geolocation service later
                .clickedAt(LocalDateTime.now())
                .build();

        producer.sendEvent(dto);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(destinationURL))
                .build();
    }

    private DeviceType getDeviceType(String formFactor) {
        if (formFactor == null) {
            return DeviceType.UNKNOWN;
        }

        formFactor = formFactor.replace("\"", "").toUpperCase();

        return switch (formFactor) {
            case "Desktop" -> DeviceType.DESKTOP;
            case "Phone", "Mobile" -> DeviceType.MOBILE;
            case "Tablet" -> DeviceType.TABLET;
            default -> DeviceType.UNKNOWN;
        };
    }

    private BrowserType getBrowserType(String browserHeader) {
        if (browserHeader == null) {
            return BrowserType.UNKNOWN;
        }

        browserHeader = browserHeader.toUpperCase();

        if (browserHeader.contains("Chrome") || browserHeader.contains("CHROMIUM"))
            return BrowserType.CHROME;

        if (browserHeader.contains("FireFox"))
            return BrowserType.FIREFOX;

        if (browserHeader.contains("Safari"))
            return BrowserType.SAFARI;

        if (browserHeader.contains("Edge"))
            return BrowserType.EDGE;

        if (browserHeader.contains("Opera"))
            return BrowserType.OPERA;

        if (browserHeader.contains("Brave"))
            return BrowserType.BRAVE;

        return BrowserType.UNKNOWN;
    }
}
