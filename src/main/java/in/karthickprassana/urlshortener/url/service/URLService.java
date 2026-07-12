package in.karthickprassana.urlshortener.url.service;

import in.karthickprassana.urlshortener.url.dto.CreateURLRequestDTO;
import in.karthickprassana.urlshortener.url.dto.SingleURLResponseDTO;
import in.karthickprassana.urlshortener.url.entity.URL;
import in.karthickprassana.urlshortener.url.repository.URLRepository;
import in.karthickprassana.urlshortener.url.utils.RandomStringUtils;
import in.karthickprassana.urlshortener.user.entity.User;
import in.karthickprassana.urlshortener.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class URLService {

    private final URLRepository urlRepository;
    private final UserRepository userRepository;
    private final RandomStringUtils randomStringUtils;

    public boolean checkIsUnique(String url) {
        return urlRepository.findShortenedURL(url).isPresent();
    }

    public SingleURLResponseDTO addURL(CreateURLRequestDTO data) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String uniqueURL = "";

        if(data.getUniqueName() != null && !data.getOriginalURL().isEmpty()) {
            if(checkIsUnique(data.getUniqueName())) {
                uniqueURL = data.getUniqueName();
            }
        }else {
            String key;
            do {
                key = RandomStringUtils.generateString(7);
            }while (!checkIsUnique(key));
            uniqueURL = key;
        }


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


    public void deleteURL(Long id) {
        URL url = urlRepository.findById(id).orElseThrow(() -> new RuntimeException("Url does not exist"));
        urlRepository.delete(url);
    }

    public void updateShortenedURL(String newShortenedURL) {
        if(!checkIsUnique(newShortenedURL)) {
            throw new RuntimeException("This name already exists");
        }

        URL url = urlRepository.findShortenedURL(newShortenedURL).orElseThrow(() -> new RuntimeException("Url does not exists"));
        url.setShortenedURL(newShortenedURL);
        urlRepository.save(url);
    }
}
