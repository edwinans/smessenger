package com.smessenger.api.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.smessenger.api.model.User;
import com.smessenger.api.repository.UserRepository;
import com.smessenger.api.exception.UsernameAlreadyExistsException;
import com.smessenger.api.exception.AuthenticationFailedException;
import com.smessenger.api.util.JwtUtil;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User registerUser(String username, String rawPassword) {
    if (userRepository.existsByUsername(username))
      throw new UsernameAlreadyExistsException();
    String hashedPassword = passwordEncoder.encode(rawPassword);
    try {
      return userRepository.save(new User(username, hashedPassword));
    } catch (DataIntegrityViolationException ex) {
      throw new UsernameAlreadyExistsException();
    }
  }

  public User loginUser(String username, String rawPassword) {
    Optional<User> userOpt = userRepository.findByUsername(username);
    return userOpt.filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
        .orElseThrow(() -> new AuthenticationFailedException());
  }

  public Iterable<User> listAllUsers() {
    return userRepository.findAll();
  }

  public String authenticateAndGetToken(String username, String rawPassword) {
    User user = loginUser(username, rawPassword);
    return JwtUtil.generateToken(user.getUsername());
  }

}
