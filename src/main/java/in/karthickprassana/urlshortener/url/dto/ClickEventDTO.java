package in.karthickprassana.urlshortener.url.dto;

import in.karthickprassana.urlshortener.url.utils.BrowserType;
import in.karthickprassana.urlshortener.url.utils.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor

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
