package in.karthickprassana.urlshortener.user.controller;

import in.karthickprassana.urlshortener.user.dto.UpdateUserData;
import in.karthickprassana.urlshortener.user.dto.UserResponseDTO;
import in.karthickprassana.urlshortener.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/update/")
@RequiredArgsConstructor

public class UpdateController {

    private final UserService userService;


    @PutMapping("profile-pic")
    public ResponseEntity<UserResponseDTO> updateUserProfilePic(@Valid @RequestBody UpdateUserData data, Authentication authentication) {

        String email = authentication.getName();
        UserResponseDTO response = userService
                .updateUserData(email, data
                );
        return ResponseEntity.ok(
                response
        );
    }
}
