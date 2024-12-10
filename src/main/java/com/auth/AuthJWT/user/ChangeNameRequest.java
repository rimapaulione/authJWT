package com.auth.AuthJWT.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@Builder
public class ChangeNameRequest {
    private String newFirstName;
    private String newLastName;
    private Optional<String> newPassword;
    private Optional<String> oldPassword;
    private String email;
}
