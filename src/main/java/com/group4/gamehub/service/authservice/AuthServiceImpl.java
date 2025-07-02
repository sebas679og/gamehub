package com.group4.gamehub.service.authservice;

import com.group4.gamehub.config.JwtService;
import com.group4.gamehub.dto.requests.auth.Login;
import com.group4.gamehub.dto.requests.auth.Register;
import com.group4.gamehub.dto.responses.auth.AuthResponse;
import com.group4.gamehub.exception.UserAlreadyExistsException;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.UserRepository;
import com.group4.gamehub.util.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the authentication service. Handles user registration and login, and generates
 * JWT tokens upon successful authentication.
 */
@Service
public class AuthServiceImpl implements AuthServiceInterface {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  /**
   * Constructs the authentication service with required dependencies.
   *
   * @param userRepository repository for user persistence
   * @param passwordEncoder encoder for hashing passwords
   * @param jwtService service for generating JWT tokens
   * @param authenticationManager Spring Security's authentication manager
   */
  public AuthServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  /**
   * Registers a new user with encoded password and default role. Fails if the email or username is
   * already in use.
   *
   * @param request the registration request containing user credentials
   * @return an {@link AuthResponse} containing the generated JWT token
   * @throws UserAlreadyExistsException if the email or username already exists
   */
  @Override
  public AuthResponse register(Register request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new UserAlreadyExistsException("E-mail already registered");
    }
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new UserAlreadyExistsException("Username already registered");
    }

    UserEntity userEntity =
        UserEntity.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.PLAYER)
            .build();

    userRepository.save(userEntity);

    String token = generateJwtToken(userEntity);
    return new AuthResponse(token);
  }

  /**
   * Authenticates a user and returns a JWT token if credentials are valid.
   *
   * @param request the login request containing username and password
   * @return an {@link AuthResponse} containing the generated JWT token
   * @throws RuntimeException if the user is not found
   */
  @Override
  public AuthResponse login(Login request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    UserEntity user =
        userRepository
            .findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("Unregistered user"));

    String token = generateJwtToken(user);
    return new AuthResponse(token);
  }

  /**
   * Generates a JWT token based on the given user's credentials.
   *
   * @param userEntity the user entity to generate a token for
   * @return a JWT token as a string
   */
  private String generateJwtToken(UserEntity userEntity) {
    return jwtService.generateToken(
        org.springframework.security.core.userdetails.User.builder()
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .roles(userEntity.getRole().name())
            .build());
  }
}
