package com.auth.AuthJWT.user;


import com.auth.AuthJWT.exeption.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void changeName(ChangeNameRequest request) {

      User user = repository.findByEmail(request.getEmail())
              .orElseThrow(()-> new UserNotFoundException("User does not exist"));

user.setFirstname(request.getNewFirstName());
       repository.save(user);
    }
    public UserByIdResponse getUserById(UUID id ){

        User user = repository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User does not exist"));

        return UserByIdResponse.builder()
                .firstname(user.getFirstname())
                .lastname((user.getLastname()))
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
