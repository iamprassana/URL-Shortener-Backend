package in.karthickprassana.urlshortener.url.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "url_stats",
        indexes = {
                @Index(name = "idx_url_stats_id", columnList = "url_id")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class URLStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_clicks")
    private Long totalClicks;

    @Column(name = "mobile_count")
    private Long mobileCount;

    @Column(name = "desktop_count")
    private Long desktopCount;

//    @Column(name = "last_clicked_at")
//    private LocalDateTime lastClickedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id", unique = true, nullable = false)
    private URL url;
}
