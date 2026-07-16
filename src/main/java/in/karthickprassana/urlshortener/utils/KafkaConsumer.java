package in.karthickprassana.urlshortener.utils;

import in.karthickprassana.urlshortener.url.dto.ClickEventDTO;
import in.karthickprassana.urlshortener.url.service.ClickEventService;
import in.karthickprassana.urlshortener.url.service.URLService;
import in.karthickprassana.urlshortener.url.service.URLStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

//    private final URLService urlService;
    private final URLStatsService urlStatsService;
    private final ClickEventService clickEventService;

    @KafkaListener(topics = "click-event", groupId = "event-consumer-group")
    public void getEvent(ClickEventDTO eventData) {
        System.out.println(eventData.getUrl());
        clickEventService.addClickEvent(eventData);
        urlStatsService.incrementData(eventData.getUrlId(), eventData.getDeviceType());
    }
}
