package com.auth.AuthJWT.reset;

import com.auth.AuthJWT.exeption.UserNotFoundException;
import com.auth.AuthJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordResetRepository passwordResetRepository;

    public PasswordResetResponse createPasswordResetToken(PasswordResetRequest email) {
        var user = userRepository.findByEmail(email.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("User does not exist"));

        passwordResetRepository.findByEmail(email.getEmail())
                .ifPresent(passwordResetRepository::delete);

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setEmail(email.getEmail());
        token.setExpires(LocalDateTime.now().plusMinutes(2));
       passwordResetRepository.save(token);

        return PasswordResetResponse.builder()
                .email(user.getEmail())
                .passwordResetToken(token.getToken())
                .build();
    }

}
