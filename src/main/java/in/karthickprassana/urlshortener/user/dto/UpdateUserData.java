package in.karthickprassana.urlshortener.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class UpdateUserData {

    @NotBlank
    private String profilePic;
    private String name;

}
