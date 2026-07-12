package in.karthickprassana.urlshortener.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class UserResponseDTO {
    private String id;
    private String name;
    private String displayName;
    private String email;
    private LocalDateTime createdAt;
}
