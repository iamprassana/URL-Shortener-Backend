package in.karthickprassana.urlshortener.url.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@RequiredArgsConstructor

public class SingleURLResponseDTO {

    private final String originalUrl;
    private final String shortenedUrl;
    private LocalDateTime createdAt;
}
