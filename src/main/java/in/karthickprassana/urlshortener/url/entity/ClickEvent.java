package in.karthickprassana.urlshortener.url.entity;

import in.karthickprassana.urlshortener.url.utils.BrowserType;
import in.karthickprassana.urlshortener.url.utils.DeviceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "click_event",
        indexes = {
                @Index(name = "idx_click_event_id", columnList = "url_id"),
                @Index(name = "idx_url_clicked", columnList = "url_id, clicked_at")
        }
)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ClickEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(name = "country")
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "device")
    private DeviceType device;

    @Enumerated(EnumType.STRING)
    @Column(name = "browser")
    private BrowserType browser;

    @Column(name = "clicked_at")
    private LocalDateTime clickedAt;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "os")
    private String os;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id", nullable = false)
    private URL url;
}

