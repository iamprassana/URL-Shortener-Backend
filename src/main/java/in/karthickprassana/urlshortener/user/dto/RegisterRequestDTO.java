package in.karthickprassana.urlshortener.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String displayName;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

    private String profile_pic;
}
