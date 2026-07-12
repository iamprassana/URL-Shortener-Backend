package in.karthickprassana.urlshortener.url.service;

import in.karthickprassana.urlshortener.url.dto.URLStatsDTO;
import in.karthickprassana.urlshortener.url.dto.URLStatsResponseDTO;
import in.karthickprassana.urlshortener.url.entity.URL;
import in.karthickprassana.urlshortener.url.entity.URLStats;
import in.karthickprassana.urlshortener.url.repository.URLRepository;
import in.karthickprassana.urlshortener.url.repository.URLStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class URLStatsService {

    private final URLStatsRepository urlStatsRepository;
    private final URLRepository urlRepository;

    public URLStatsResponseDTO getUrlStats(Long urlId) {

        Optional<URLStats> optionalURLStats = urlStatsRepository.findById(urlId);

        if(optionalURLStats.isEmpty()) {
            throw new RuntimeException("No stats exist for this URL");
        }

        URLStats urlStats = optionalURLStats.get();

        return URLStatsResponseDTO
                .builder().
                id(urlStats.getId())
                .desktopCount(urlStats.getDesktopCount())
                .mobileCount(urlStats.getMobileCount())
//                .lastClickedAt(urlStats.getLastClickedAt())
                .totalClicks(urlStats.getTotalClicks())
                .build();
    }

    public void updateUrlStats(Long statsId, URLStatsDTO data) {
        URLStats stats = urlStatsRepository.findById(statsId).orElseThrow(() -> new RuntimeException("URL does not exist"));
        stats.setDesktopCount(data.getDesktopCount());
        stats.setMobileCount(data.getMobileCount());
        stats.setTotalClicks(data.getTotalClicks());

        urlStatsRepository.save(stats);
    }

    public void createUrlStats(Long urlId) {
        URL url = urlRepository.findById(urlId).orElseThrow(() -> new RuntimeException("URL does not exist"));
        URLStats newStats = URLStats
                .builder()
                .desktopCount(0L)
                .totalClicks(0L)
                .mobileCount(0L)
                .url(url)
                .build();
        urlStatsRepository.save(newStats);

        //TODO: Return the created stats
    }
}
