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

        System.out.println(email);
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User with the provided email does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        User user = userOptional.get();

        if (user.getVerified() != null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User is already verified.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        VerificationToken token = verificationTokenService.createToken(email);
        Map<String, String> response = new HashMap<>();
        response.put("token", token.getToken());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyToken(@RequestParam String token) {

        System.out.println(token);
        System.out.println("test");
        boolean isVerified = verificationTokenService.verifyToken(token);
        Map<String, String> response = new HashMap<>();

        if (isVerified) {
            response.put("message", "User verified successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid or expired token.");
            return ResponseEntity.status(400).body(response);
        }
    }

}
