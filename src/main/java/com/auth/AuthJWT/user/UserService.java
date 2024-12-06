package com.auth.AuthJWT.user;


import com.auth.AuthJWT.exeption.UserNotFoundException;
import com.auth.AuthJWT.token.Token;
import com.auth.AuthJWT.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private  final TokenRepository tokenRepository;

    public void changeName(ChangeNameRequest request) {

      User user = repository.findByEmail(request.getEmail())
              .orElseThrow(()-> new UserNotFoundException("User does not exist"));

user.setFirstname(request.getNewFirstName());
       repository.save(user);
    }

    public UserByIdResponse getUserById(UUID id ){
        System.out.println(id);
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
