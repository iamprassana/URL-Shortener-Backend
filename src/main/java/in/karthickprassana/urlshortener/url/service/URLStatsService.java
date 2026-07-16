package in.karthickprassana.urlshortener.url.service;

import in.karthickprassana.urlshortener.url.dto.CreateURLStatsResponseDTO;
import in.karthickprassana.urlshortener.url.dto.URLStatsDTO;
import in.karthickprassana.urlshortener.url.dto.URLStatsResponseDTO;
import in.karthickprassana.urlshortener.url.entity.URL;
import in.karthickprassana.urlshortener.url.entity.URLStats;
import in.karthickprassana.urlshortener.url.repository.URLRepository;
import in.karthickprassana.urlshortener.url.repository.URLStatsRepository;
import in.karthickprassana.urlshortener.url.utils.DeviceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class URLStatsService {

    private final URLStatsRepository urlStatsRepository;
    private final URLRepository urlRepository;

    public void incrementData(Long id, DeviceType type) {
        URLStats stats = urlStatsRepository.findByUrlId(id).orElseThrow(() -> new RuntimeException("No stat found for the given url."));
        stats.setTotalClicks(stats.getTotalClicks() + 1);
        if(type == DeviceType.DESKTOP) {
            stats.setDesktopCount(stats.getDesktopCount() + 1);
        }else if(type == DeviceType.MOBILE) {
            stats.setMobileCount(stats.getMobileCount() + 1);
        }
        urlStatsRepository.save(stats);
    }

    public URLStatsResponseDTO getUrlStats(Long urlId) {

        Optional<URLStats> optionalURLStats = urlStatsRepository.findById(urlId);

        if(optionalURLStats.isEmpty()) {
            throw new RuntimeException("No stats exist for this URL");
        }

        URLStats urlStats = optionalURLStats.get();

        return URLStatsResponseDTO
                .builder()
                .statsId(urlStats.getId())
                .desktopCount(urlStats.getDesktopCount())
                .mobileCount(urlStats.getMobileCount())
//                .lastClickedAt(urlStats.getLastClickedAt())
                .totalClicks(urlStats.getTotalClicks())
                .build();
    }

//    public URLStatsResponseDTO updateUrlStats(Long statsId, URLStatsDTO data) {
//        URLStats stats = urlStatsRepository.findById(statsId).orElseThrow(() -> new RuntimeException("URL does not exist"));
//        stats.setDesktopCount(data.getDesktopCount());
//        stats.setMobileCount(data.getMobileCount());
//        stats.setTotalClicks(data.getTotalClicks());
//
//        stats = urlStatsRepository.save(stats);
//        return URLStatsResponseDTO
//                .builder()
//                .statsId(stats.getId())
//                .desktopCount(stats.getDesktopCount())
//                .mobileCount(stats.getMobileCount())
//                .totalClicks(stats.getTotalClicks())
//                .build();
//    }

    public CreateURLStatsResponseDTO createUrlStats(Long urlId) {
        URL url = urlRepository.findById(urlId).orElseThrow(() -> new RuntimeException("URL does not exist"));
        URLStats newStats = URLStats
                .builder()
                .desktopCount(0L)
                .totalClicks(0L)
                .mobileCount(0L)
                .url(url)
                .build();
        newStats = urlStatsRepository.save(newStats);;

        CreateURLStatsResponseDTO responseDTO = CreateURLStatsResponseDTO
                .builder()
                .statsId(newStats.getId())
                .desktopCount(newStats.getDesktopCount())
                .mobileCount(newStats.getMobileCount())
                .totalClicks(newStats.getTotalClicks())
                .build();

        return responseDTO;
    }
}
