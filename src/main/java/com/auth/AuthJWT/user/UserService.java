package com.auth.AuthJWT.user;


import com.auth.AuthJWT.exeption.UserNotFoundException;
import com.auth.AuthJWT.token.Token;
import com.auth.AuthJWT.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import com.auth.AuthJWT.user.ChangeNameRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private  final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public void changeName(ChangeNameRequest request) {

      User user = repository.findByEmail(request.getEmail())
              .orElseThrow(()-> new UserNotFoundException("User does not exist"));

        user.setFirstname(request.getNewFirstName());
        user.setLastname(request.getNewLastName());
        user.setRole(request.getRole());

       if (request.getOldPassword().isPresent() & request.getNewPassword().isPresent()) {

            String oldPassword = request.getOldPassword().get();
            String newPassword = request.getNewPassword().get();

            if(!passwordEncoder.matches(oldPassword, user.getPassword()))
                throw new IllegalArgumentException("Old password is invalid!");

            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new IllegalArgumentException("New password cannot be the same as the old one");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        repository.save(user);
    }

    public UserByIdResponse getUserById(UUID id ){
        User user = repository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User does not exist"));

        Optional<String> jwtToken = tokenRepository.findAllValidTokenByUser(id)
                .stream()
                .map(Token::getToken)
                .findFirst();

        return UserByIdResponse.builder()
                .firstname(user.getFirstname())
                .token(jwtToken.orElse(null))
                .lastname((user.getLastname()))
                .email(user.getEmail())
                .role(user.getRole())
                .id(user.getId())
                .build();
    }
}
