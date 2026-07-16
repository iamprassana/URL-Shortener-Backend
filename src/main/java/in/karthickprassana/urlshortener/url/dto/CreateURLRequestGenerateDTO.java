package in.karthickprassana.urlshortener.url.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class CreateURLRequestGenerateDTO {
    private final String originalUrl;
    private final String urlName;
}
