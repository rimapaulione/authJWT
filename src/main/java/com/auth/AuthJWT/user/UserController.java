package com.auth.AuthJWT.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping
    public ResponseEntity<?> changeName(
            @RequestBody ChangeNameRequest request
    ) {
        service.changeName(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user")
    public ResponseEntity<?> userById (
            @RequestBody Map<String, String> request
            ){
        String idString = request.get("id");
        UUID id = UUID.fromString(idString);
        return ResponseEntity.ok(service.getUserById(id));
    }
}
