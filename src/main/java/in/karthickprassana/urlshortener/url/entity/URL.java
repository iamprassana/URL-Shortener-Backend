package in.karthickprassana.urlshortener.url.entity;

import in.karthickprassana.urlshortener.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name="url",
        indexes = {
                @Index(name = "idx_shortened_url", columnList = "shortened_url"),
                @Index(name = "idx_destination_url", columnList = "destination_url")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "destination_url", nullable = false)
    private String destinationURL;

    @Column(name = "shortened_url", nullable = false)
    private String shortenedURL;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "user_id", nullable = false)
    private User user;

}