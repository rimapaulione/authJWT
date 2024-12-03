package com.auth.AuthJWT.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangeNameRequest request
    ) {
        service.changeName(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/getUser")
    public ResponseEntity<?> userById (
            @RequestBody String request
            ){
        service.getUserById(request);
        return ResponseEntity.ok().build();
    }
}
