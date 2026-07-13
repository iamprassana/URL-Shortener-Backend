package in.karthickprassana.urlshortener.url.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor

public class CreateURLStatsResponseDTO {

    private Long statsId;
    private Long mobileCount;
    private Long desktopCount;
    private Long totalClicks;

}
