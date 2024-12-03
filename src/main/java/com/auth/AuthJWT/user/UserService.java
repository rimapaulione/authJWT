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
}
