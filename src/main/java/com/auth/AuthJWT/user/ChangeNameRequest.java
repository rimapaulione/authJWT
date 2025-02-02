package com.auth.AuthJWT.user;


import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeNameRequest {
    private String newFirstName;
    private String newLastName;
    private Role role;
    private Optional<String> newPassword;
    private Optional<String> oldPassword;
    private String email;
}
