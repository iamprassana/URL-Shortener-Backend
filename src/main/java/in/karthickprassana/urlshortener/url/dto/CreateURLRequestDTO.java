package in.karthickprassana.urlshortener.url.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class CreateURLRequestDTO {
    private final String originalURL;
    private final String urlName;
//    private final String userId;
    private final String uniqueName;
}
