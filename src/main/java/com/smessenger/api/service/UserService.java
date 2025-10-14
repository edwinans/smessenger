package com.smessenger.api.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.smessenger.api.model.User;
import com.smessenger.api.repository.UserRepository;
import com.smessenger.api.exception.UsernameAlreadyExistsException;
import com.smessenger.api.config.JwtUtil;
import com.smessenger.api.dto.UserDTO;
import com.smessenger.api.exception.AuthenticationFailedException;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  private UserDTO toDto(User user) {
    return new UserDTO(user.getId(), user.getUsername());
  }

  public UserDTO registerUser(String username, String rawPassword) {
    if (userRepository.existsByUsername(username))
      throw new UsernameAlreadyExistsException();
    String hashedPassword = passwordEncoder.encode(rawPassword);
    try {
      User createdUser = userRepository.save(new User(username, hashedPassword));
      return toDto(createdUser);
    } catch (DataIntegrityViolationException ex) {
      throw new UsernameAlreadyExistsException();
    }
  }

  public User loginUser(String username, String rawPassword) {
    Optional<User> userOpt = userRepository.findByUsername(username);
    return userOpt.filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
        .orElseThrow(() -> new AuthenticationFailedException());
  }

  public Iterable<UserDTO> listAllUsers() {
    return userRepository.findAll().stream().map(this::toDto).toList();
  }

  public String authenticateAndGetToken(String username, String rawPassword) {
    User user = loginUser(username, rawPassword);
    return JwtUtil.generateToken(user.getUsername());
  }

}
