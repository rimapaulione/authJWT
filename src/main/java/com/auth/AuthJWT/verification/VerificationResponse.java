package com.auth.AuthJWT.verification;

import com.auth.AuthJWT.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationResponse {
    private UUID id;
    private String email;
    private LocalDateTime verified;
}

