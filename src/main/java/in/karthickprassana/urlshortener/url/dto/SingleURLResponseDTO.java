package in.karthickprassana.urlshortener.url.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class SingleURLResponseDTO {
    private final Long id;
    private final String name;
    private final String originalUrl;
    private final String shortenedUrl;
    private LocalDateTime createdAt;
}
