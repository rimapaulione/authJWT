package com.auth.AuthJWT.auth;

import com.auth.AuthJWT.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private String verification;
}
