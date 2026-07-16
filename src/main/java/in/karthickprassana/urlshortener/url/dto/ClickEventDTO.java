package in.karthickprassana.urlshortener.url.dto;

import in.karthickprassana.urlshortener.url.utils.BrowserType;
import in.karthickprassana.urlshortener.url.utils.DeviceType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ClickEventDTO {

    private DeviceType deviceType;
    private BrowserType browserType;
    private String country;
    private String ip;
    private String os;
    private Long urlId;
    private LocalDateTime clickedAt;
    private String url;
}
