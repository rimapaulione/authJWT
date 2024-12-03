package com.auth.AuthJWT.user;


import com.auth.AuthJWT.exeption.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public UserByIdResponse getUserById(String request ){

        User user = repository.findById(request)
                .orElseThrow(()-> new UserNotFoundException("User does not exist"));
        return UserByIdResponse.builder()
                .firstname(user.getFirstname())
                .lastname((user.getLastname()))
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
