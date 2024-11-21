package com.auth.AuthJWT.reset;

import com.auth.AuthJWT.verification.VerificationTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reset")
public class PasswordResetController {

    private final PasswordResetService service;

    @PostMapping("/create")
    public ResponseEntity<PasswordResetResponse> authenticate(
            @RequestBody PasswordResetRequest request){
        return ResponseEntity.ok(service.createPasswordResetToken(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(
            @RequestBody PasswordResetVerifyRequest request) {
        return ResponseEntity.ok(service.verifyPasswordResetToken((request)));
    }
}
