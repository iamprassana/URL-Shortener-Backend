package in.karthickprassana.urlshortener.user.controller;

import in.karthickprassana.urlshortener.user.dto.*;
import in.karthickprassana.urlshortener.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserResponseDTO> getUserData(Authentication authentication) {
        String email = authentication.getName();
        UserResponseDTO response = userService.getUser(email);
        return ResponseEntity.ok(response);
    }
}
