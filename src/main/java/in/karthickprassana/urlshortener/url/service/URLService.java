package in.karthickprassana.urlshortener.url.service;

import in.karthickprassana.urlshortener.url.dto.*;
import in.karthickprassana.urlshortener.url.entity.URL;
import in.karthickprassana.urlshortener.url.repository.URLRepository;
import in.karthickprassana.urlshortener.url.utils.RandomStringUtils;
import in.karthickprassana.urlshortener.user.entity.User;
import in.karthickprassana.urlshortener.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class URLService {

    private final URLRepository urlRepository;
    private final UserRepository userRepository;
    private final URLStatsService urlStatsService;
    private final ClickEventService clickEventService;
    private final RandomStringUtils randomStringUtils;

    public boolean checkIsUnique(String url) {
        return urlRepository.existsURLByShortenedURL(url);
    }

    public String generateURL() {
        String key;
        do {
            key = randomStringUtils.generateString(7);
        }while (!checkIsUnique(key));
        return key;
    }

    public String getOriginalURL(String shortenedURL) {
        return urlRepository
                .findByShortenedURL(shortenedURL)
                .orElseThrow(
                        () -> new RuntimeException("URL does not exist")
                )
                .getDestinationURL();
    }

    private SingleURLResponseDTO getSingleURLResponseDTO(CreateURLRequestDTO data, String email, String uniqueURL) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User does not exist."));
        URL newURL = URL
                .builder()
                .destinationURL(data.getOriginalURL())
                .shortenedURL(uniqueURL)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        urlRepository.save(newURL);

        return SingleURLResponseDTO
                .builder()
                .originalUrl(data.getOriginalURL())
                .shortenedUrl(uniqueURL)
                .build();
    }

    public SingleURLResponseDTO addCustomURL(CreateURLRequestDTO data) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String uniqueURL = "";

        if(data.getUniqueName() != null && !data.getOriginalURL().isEmpty()) {
            if(checkIsUnique(data.getUniqueName())) {
                uniqueURL = data.getUniqueName();
            }else {
                throw new RuntimeException("URL already taken");
            }
        }


        return getSingleURLResponseDTO(data, email, uniqueURL);
    }

    public SingleURLResponseDTO createWithGeneratedURL(CreateURLRequestDTO data) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String uniqueURL = generateURL();

        return getSingleURLResponseDTO(data, email, uniqueURL);
    }




    public void deleteURL(Long id) {
        URL url = urlRepository.findById(id).orElseThrow(() -> new RuntimeException("URL does not exist"));
        urlRepository.delete(url);
    }

    public void updateShortenedURL(String newShortenedURL) {
        if(!checkIsUnique(newShortenedURL)) {
            throw new RuntimeException("This name already exists");
        }

        URL url = urlRepository.findByShortenedURL(newShortenedURL).orElseThrow(() -> new RuntimeException("URL does not exists"));
        url.setShortenedURL(newShortenedURL);
        urlRepository.save(url);
    }

    public List<DetailedURLResponseDTO> getURLs() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        List<URL> urls = urlRepository.findByUserEmail(email);

        return urls.stream()
                .map(url -> {
                    Long id = url.getId();

                    SingleURLResponseDTO urlDetail = SingleURLResponseDTO.builder()
                            .id(id)
                            .originalUrl(url.getDestinationURL())
                            .shortenedUrl(url.getShortenedURL())
                            .createdAt(url.getCreatedAt())
                            .build();

                    URLStatsResponseDTO urlStats = urlStatsService.getUrlStats(id);

                    List<ClickEventDTO> clickEvents =
                            clickEventService.getClickEvents(id, 1, 20);

                    return DetailedURLResponseDTO.builder()
                            .urlDetail(urlDetail)
                            .urlStats(urlStats)
                            .clickEvents(clickEvents)
                            .build();
                })
                .toList();
    }

    public DetailedURLResponseDTO getURL(String shortenedURL) {
        URL response =  urlRepository.findByShortenedURL(shortenedURL).orElseThrow(() -> new RuntimeException("URL does not exist"));
        Long id = response.getId();
        SingleURLResponseDTO data = SingleURLResponseDTO
                .builder()
                .id(id)
                .originalUrl(response.getDestinationURL())
                .shortenedUrl(response.getShortenedURL())
                .build();
        URLStatsResponseDTO urlStats = urlStatsService.getUrlStats(id);
        List<ClickEventDTO> clickEvents = clickEventService.getClickEvents(id, 1, 20);

        return DetailedURLResponseDTO
                .builder()
                .urlDetail(data)
                .urlStats(urlStats)
                .clickEvents(clickEvents)
                .build();
    }
}
