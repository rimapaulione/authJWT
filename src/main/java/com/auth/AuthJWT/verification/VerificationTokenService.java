package com.auth.AuthJWT.verification;

import com.auth.AuthJWT.exeption.UserNotFoundException;
import com.auth.AuthJWT.user.User;
import com.auth.AuthJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public VerificationToken createVerificationToken(String email) {
        verificationTokenRepository.findByEmail(email)
                .ifPresent(verificationTokenRepository::delete);

        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setEmail(email);
        token.setExpires(LocalDateTime.now().plusMinutes(2));
        return verificationTokenRepository.save(token);

    }

    public VerificationTokenResponse verifyVerificationToken(String tokenValue) {
        Optional<VerificationToken> tokenOpt = verificationTokenRepository.findByToken(tokenValue);
        if (tokenOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid or expired token.");
        }
        VerificationToken token = tokenOpt.get();

        if (token.getExpires().isBefore(LocalDateTime.now())) {
            verificationTokenRepository.delete(token);
            throw new UserNotFoundException("Invalid or expired token.");
        }

        Optional<User> userOpt = userRepository.findByEmail(token.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setVerified(LocalDateTime.now());
            userRepository.save(user);
        }
        var user = userRepository.findByEmail(token.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        verificationTokenRepository.delete(token);

        return VerificationTokenResponse.builder()
                .verified(user.getVerified())
                .id(user.getId())
                .email(user.getEmail())
        .build();
    }

}
