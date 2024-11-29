package com.auth.AuthJWT.resetPassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetResponse {

    private String email;
    private String passwordResetToken;
    private LocalDateTime expires;
}
