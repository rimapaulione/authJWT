package com.auth.AuthJWT.verification;

import com.auth.AuthJWT.user.User;
import com.auth.AuthJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public VerificationToken createToken(String email) {
        verificationTokenRepository.findByEmail(email).ifPresent(verificationTokenRepository::delete);

        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setEmail(email);
        token.setExpires(LocalDateTime.now().plusMinutes(2));
        return verificationTokenRepository.save(token);

    }

    public boolean verifyToken(String tokenValue) {
        Optional<VerificationToken> tokenOpt = verificationTokenRepository.findByToken(tokenValue);
        if (tokenOpt.isEmpty()) {
            return false;
        }
        VerificationToken token = tokenOpt.get();

        if (token.getExpires().isBefore(LocalDateTime.now())) {
            verificationTokenRepository.delete(token);
            return false;
        }

        Optional<User> userOpt = userRepository.findByEmail(token.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setVerified(LocalDateTime.now());
            userRepository.save(user);
        }

        verificationTokenRepository.delete(token);
        return true;
    }

}
