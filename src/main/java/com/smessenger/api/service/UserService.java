package com.smessenger.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.smessenger.api.model.User;
import com.smessenger.api.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean registerUser(String username, String rawPassword) {
    if (userRepository.existsByUsername(username))
      return false;
    String hashedPassword = passwordEncoder.encode(rawPassword);
    userRepository.save(new User(username, hashedPassword));
    return true;
  }

  public Optional<User> loginUser(String username, String rawPassword) {
    Optional<User> userOpt = userRepository.findByUsername(username);
    if (userOpt.isPresent()) {
      User user = userOpt.get();
      if (passwordEncoder.matches(rawPassword, user.getPassword())) {
        return Optional.of(user);
      }
    }
    return Optional.empty();
  }

  public Iterable<User> listAllUsers() {
    return userRepository.findAll();
  }

}
