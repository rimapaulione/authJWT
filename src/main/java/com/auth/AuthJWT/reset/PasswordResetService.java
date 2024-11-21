package com.auth.AuthJWT.reset;

import com.auth.AuthJWT.exeption.UserNotFoundException;
import com.auth.AuthJWT.user.User;
import com.auth.AuthJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final PasswordEncoder passwordEncoder;

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
                .expires(token.getExpires())
                .build();
    }
    public Boolean verifyPasswordResetToken( PasswordResetVerifyRequest request){
        Optional<PasswordResetToken> tokenOpt = passwordResetRepository.findByToken(request.getToken());

        if (tokenOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid or expired token.");
        }
        PasswordResetToken token = tokenOpt.get();
        if (token.getExpires().isBefore(LocalDateTime.now())) {
            passwordResetRepository.delete(token);
            throw new UserNotFoundException("Invalid or expired token.");
        }
        Optional<User> userOpt = userRepository.findByEmail(token.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
        }
            passwordResetRepository.delete(token);

        return true;

    }
}
