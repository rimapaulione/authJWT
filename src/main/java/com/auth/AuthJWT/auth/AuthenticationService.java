package com.auth.AuthJWT.auth;


import com.auth.AuthJWT.config.JwtService;
import com.auth.AuthJWT.exeption.UserNotFoundException;
import com.auth.AuthJWT.exeption.UserNotVerifiedException;
import com.auth.AuthJWT.token.Token;
import com.auth.AuthJWT.token.TokenRepository;
import com.auth.AuthJWT.token.TokenType;
import com.auth.AuthJWT.user.Role;
import com.auth.AuthJWT.user.User;
import com.auth.AuthJWT.user.UserRepository;
import com.auth.AuthJWT.verification.VerificationToken;
import com.auth.AuthJWT.verification.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
   private final UserRepository userRepository;
   private final TokenRepository tokenRepository;
   private final PasswordEncoder passwordEncoder;
   private final JwtService jwtService;
   private final AuthenticationManager authenticationManager;
   private final VerificationTokenService verificationTokenService;

    public AuthenticationResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use: " + request.getEmail());
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .verified(null)
                .build();

        var savedUser = userRepository.save(user);
        VerificationToken verificationToken = verificationTokenService
                .createVerificationToken(savedUser.getEmail());

        return AuthenticationResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .token(null)
                .role(user.getRole())
                .verification(verificationToken.getToken())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
       authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        if (user.getVerified() == null) {
            VerificationToken verificationToken = verificationTokenService
                    .createVerificationToken(user.getEmail());
            var verToken = verificationToken.getToken();
            throw new UserNotVerifiedException("Not verified " + verToken + " " + user.getEmail());
        }

        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole())
                .token(jwtToken).build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public LoginResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
        return LoginResponse.builder().verified(user.getVerified()).build();
    }



}
