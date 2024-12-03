package com.auth.AuthJWT.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserByIdResponse {
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
}