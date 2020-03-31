package com.springit.springit_backend.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import com.springit.springit_backend.dto.AuthenticationResponse;
import com.springit.springit_backend.dto.LoginRequest;
import com.springit.springit_backend.dto.RegisterRequest;
import com.springit.springit_backend.exception.SpringitException;
import com.springit.springit_backend.model.NotificationEmail;
import com.springit.springit_backend.model.User;
import com.springit.springit_backend.model.VerificationToken;
import com.springit.springit_backend.repository.UserRepository;
import com.springit.springit_backend.repository.VerificationTokenRepository;
import com.springit.springit_backend.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final VerificationTokenRepository verificationTokenRepository;

  private final MailService mailService;

  private final AuthenticationManager authenticationManager;

  private final JwtProvider jwtProvider;

  @Transactional
  public void signup(RegisterRequest registerRequest) {
    User user = new User();
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setDateCreated(Instant.now());
    user.setEnabled(false);

    userRepository.save(user);

    String token = generateVerificationToken(user);
    mailService.sendMail(new NotificationEmail("Please Activate your Account",
            user.getEmail(), "Thank you for signing up to Spring Reddit, " +
            "please click on the below url to activate your account : " +
            "http://localhost:8080/api/auth/accountVerification/" + token));
  }

  @Transactional(readOnly = true)
  User getCurrentUser() {
    org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
            getContext().getAuthentication().getPrincipal();
    return userRepository.findByUsername(principal.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
  }

  @Transactional
  void fetchUserAndEnable(VerificationToken verificationToken) {
    String username = verificationToken.getUser().getUsername();
    User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringitException("User not found with name - " + username));
    user.setEnabled(true);
    userRepository.save(user);
  }

  private String generateVerificationToken(User user) {
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(token);
    verificationToken.setUser(user);

    verificationTokenRepository.save(verificationToken);
    return token;
  }

  public void verifyAccount(String token) {
    Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
    fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringitException("Invalid Token")));
  }

  public AuthenticationResponse login(LoginRequest loginRequest) {
    Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authenticate);
    String token = jwtProvider.generateToken(authenticate);
    return new AuthenticationResponse(token, loginRequest.getUsername());
  }

}


