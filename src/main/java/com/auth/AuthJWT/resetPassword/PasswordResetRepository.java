package com.auth.AuthJWT.resetPassword;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, UUID> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByEmail(String email);

 void deleteByEmail(String email);
}
