package in.karthickprassana.urlshortener.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class LoginUserResponseDTO {
    private UserResponseDTO userResponseDTO;
    private String token;
}
