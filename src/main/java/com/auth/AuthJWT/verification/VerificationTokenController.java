package com.auth.AuthJWT.verification;

import com.auth.AuthJWT.user.User;
import com.auth.AuthJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/verification")
public class VerificationTokenController {

    private final VerificationTokenService verificationTokenService;
    private  final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createVerificationToken(
            @RequestParam String email) {

       VerificationToken token = verificationTokenService.createToken(email);
       Map<String, String> response = new HashMap<>();
       response.put("token", token.getToken());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<VerificationResponse> verifyToken(
            @RequestParam String token) {
        return ResponseEntity.ok(verificationTokenService.verifyToken(token));
    }


}
