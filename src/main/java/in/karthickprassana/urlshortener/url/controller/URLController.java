package in.karthickprassana.urlshortener.url.controller;


import in.karthickprassana.urlshortener.url.dto.CreateURLRequestDTO;
import in.karthickprassana.urlshortener.url.dto.DetailedURLResponseDTO;
import in.karthickprassana.urlshortener.url.dto.SingleURLResponseDTO;
import in.karthickprassana.urlshortener.url.service.ClickEventService;
import in.karthickprassana.urlshortener.url.service.URLService;
import in.karthickprassana.urlshortener.url.service.URLStatsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/url/")
@RequiredArgsConstructor
public class URLController {

    private URLService urlService;
    private ClickEventService clickEventService;
    private URLStatsService urlStatsService;

    @PostMapping("create-custom")
    public ResponseEntity<SingleURLResponseDTO> createWithCustomURL(@RequestBody @Valid CreateURLRequestDTO data) {

        return ResponseEntity.ok(urlService.addCustomURL(data));
    }

    @PostMapping("generate-url")
    public ResponseEntity<SingleURLResponseDTO> generateCustomURL(@RequestBody @Valid CreateURLRequestDTO data) {
        return ResponseEntity.ok(urlService.createWithGeneratedURL(data));
    }

    @GetMapping("get-urls")
    public ResponseEntity<List<DetailedURLResponseDTO>> getURLS() {
        return ResponseEntity.ok(urlService.getURLs());
    }

    @GetMapping("get-url/{url}")
    public ResponseEntity<DetailedURLResponseDTO> getURL(@PathVariable String url) {
        return ResponseEntity.ok(urlService.getURL(url));
    }

    @DeleteMapping("delete-url/{id}")
    public ResponseEntity<String> deleteURL(@PathVariable Long id) {
        urlService.deleteURL(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @GetMapping("get-unique-url")
    public ResponseEntity<String> getUniqueURL() {
        return ResponseEntity.ok(urlService.generateURL());
    }
}
