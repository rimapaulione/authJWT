package com.auth.AuthJWT.resetPassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetVerifyRequest {
    private String token;
    private String password;
    private String oldPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
}
