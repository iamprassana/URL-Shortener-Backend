package in.karthickprassana.urlshortener.utils;

import in.karthickprassana.urlshortener.url.dto.ClickEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, ClickEventDTO> kafkaTemplate;

    private final String TOPIC = "click-event";

    public void sendEvent(ClickEventDTO data) {
        kafkaTemplate.send(TOPIC, data);
    }
}
