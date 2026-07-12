package in.karthickprassana.urlshortener.url.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class URLStatsResponseDTO {

    private Long id;
    private Long totalClicks;
    private Long mobileCount;
    private Long desktopCount;
    private LocalDateTime lastClickedAt;
}
