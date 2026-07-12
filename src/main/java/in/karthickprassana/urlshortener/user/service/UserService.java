package in.karthickprassana.urlshortener.user.service;

import in.karthickprassana.urlshortener.user.dto.*;
import in.karthickprassana.urlshortener.user.entity.User;
import in.karthickprassana.urlshortener.user.repository.UserRepository;
import in.karthickprassana.urlshortener.utils.JwtUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordConfig;
    private final JwtUtil jwtUtil;

    public LoginUserResponseDTO login(LoginUserDTO userData) {
        Optional<User> optionalUser = userRepository.findByEmail(userData.getEmail());

        if(optionalUser.isEmpty()) {
            throw new RuntimeException("User does not exist");
        }

        User user = optionalUser.get();

        boolean isPasswordMatch = passwordConfig.matches(userData.getPassword(), user.getPassword());

        if(!isPasswordMatch) {
            throw new RuntimeException("Password is incorrect.");
        }

        String token = "Bearer " + jwtUtil.generateToken(userData.getEmail());

        UserResponseDTO response = UserResponseDTO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .createdAt(user.getCreatedAt())
                .build();
        return LoginUserResponseDTO
                .builder()
                .userResponseDTO(response)
                .token(token)
                .build();
    }

    public UserResponseDTO registerUser(RegisterRequestDTO userData) {


        String hashedPassword = passwordConfig.encode(userData.getPassword());

        boolean isUniqueDisplayName = isUniqueDisplayName(userData.getDisplayName());

        if(!isUniqueDisplayName) {
            throw new  IllegalArgumentException(userData.getDisplayName() + " is already taken.");
        }

        User user = User
                .builder()
                .name(userData.getName())
                .displayName(userData.getDisplayName())
                .email(userData.getEmail())
                .password(hashedPassword)
                .profilePic(userData.getProfile_pic())
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return UserResponseDTO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public UserResponseDTO updateUserData(String email, UpdateUserData data) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new RuntimeException("User does not exist");
        }

        User user = optionalUser.get();
        user.setProfilePic(data.getProfilePic());
        user.setName(data.getName());

        userRepository.save(user);

        return UserResponseDTO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public UserResponseDTO getUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new RuntimeException("User does not exist");
        }
        User user = optionalUser.get();
        return UserResponseDTO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }


    @Deprecated
    public UserResponseDTO updateName(String userName, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User does not exist");
        }

        User user = optionalUser.get();
        user.setName(userName);
        userRepository.save(user);

        return UserResponseDTO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    private boolean isUniqueDisplayName(String userDisplayName) {
        Optional<User> optionalUser = userRepository.findByName(userDisplayName);

        return optionalUser.isEmpty();
    }

}
