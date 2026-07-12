package in.karthickprassana.urlshortener.url.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class URLStatsDTO {

    private Long urlId;
    private Long totalClicks;
    private Long mobileCount;
    private Long desktopCount;
}
