package com.auth.AuthJWT.reset;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reset")
public class PasswordResetController {

    private final PasswordResetService service;

    @PostMapping("/create")
    public ResponseEntity<PasswordResetResponse> authenticate(
            @RequestBody PasswordResetRequest request){
        System.out.println(request);
        return ResponseEntity.ok(service.createPasswordResetToken(request));
    }
}
