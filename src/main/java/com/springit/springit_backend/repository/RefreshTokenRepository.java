package com.springit.springit_backend.repository;

import java.util.Optional;
import com.springit.springit_backend.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByToken(String token);

  void deleteByToken(String token);

}
