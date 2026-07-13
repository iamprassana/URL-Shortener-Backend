package in.karthickprassana.urlshortener.url.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder

public class DetailedURLResponseDTO {

    private SingleURLResponseDTO urlDetail;
    private URLStatsResponseDTO urlStats;
    private List<ClickEventDTO> clickEvents;
}
