package in.karthickprassana.urlshortener.url.service;

import in.karthickprassana.urlshortener.url.dto.ClickEventDTO;
import in.karthickprassana.urlshortener.url.entity.ClickEvent;
import in.karthickprassana.urlshortener.url.entity.URL;
import in.karthickprassana.urlshortener.url.repository.ClickEventRepository;
import in.karthickprassana.urlshortener.url.repository.URLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor

public class ClickEventService {
    private final ClickEventRepository clickEventRepository;
    private final URLRepository urlRepository;

    public void addClickEvent(ClickEventDTO data) {
        URL url = urlRepository.findShortenedURL(data.getUrl()).orElseThrow(() -> new RuntimeException("No url exists"));

        ClickEvent clickEvent = ClickEvent
                .builder()
                .os(data.getOs())
                .browser(data.getBrowserType())
                .device(data.getDeviceType())
                .clickedAt(LocalDateTime.now())
                .ipAddress(data.getIp())
                .country(data.getCountry())
                .url(url)
                .build();

        clickEventRepository.save(clickEvent);
    }

    public List<ClickEventDTO> getClickEvents(Long urlId, int pageNo, int size) {

        if(!urlRepository.existsById(urlId)) {
            throw new RuntimeException("Url does not exist");

    }

        Pageable pageable = PageRequest.of(pageNo, size, Sort.by("clicked_at").descending());

        List<ClickEvent> response = clickEventRepository.findByUrlId(urlId, pageable)
                .orElseThrow(() -> new RuntimeException("Something went wrong." ));
        return response.stream()
                .map(event -> ClickEventDTO
                        .builder()
                        .deviceType(event.getDevice())
                        .browserType(event.getBrowser())
                        .country(event.getCountry())
                        .ip(event.getIpAddress())
                        .os(event.getOs())
                        .urlId(event.getUrl().getId())
                        .clickedAt(event.getClickedAt())
                        .url(event.getUrl().getDestinationURL())
                        .build()
                ).toList();

    }
}
