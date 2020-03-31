package com.springit.springit_backend.repository;

import java.util.Optional;
import com.springit.springit_backend.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

  Optional<VerificationToken> findByToken(String token);

}
