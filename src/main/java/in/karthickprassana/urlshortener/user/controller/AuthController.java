package in.karthickprassana.urlshortener.user.controller;

import in.karthickprassana.urlshortener.user.dto.LoginUserDTO;
import in.karthickprassana.urlshortener.user.dto.LoginUserResponseDTO;
import in.karthickprassana.urlshortener.user.dto.RegisterRequestDTO;
import in.karthickprassana.urlshortener.user.dto.UserResponseDTO;
import in.karthickprassana.urlshortener.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor

public class AuthController {

    private final UserService userService;

    @PostMapping("create")
    public ResponseEntity<UserResponseDTO> signUp(@Valid @RequestBody RegisterRequestDTO userData) {
        UserResponseDTO response = userService.registerUser(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                response
        );
    }

    @PostMapping("login")
    public ResponseEntity<LoginUserResponseDTO> login(@Valid @RequestBody LoginUserDTO userData) {
        LoginUserResponseDTO response = userService.login(userData);
        return ResponseEntity.ok(response);
    }
}
