package com.smessenger.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smessenger.api.model.User;
import com.smessenger.api.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public boolean registerUser(String username) {
    if (userRepository.existsByUsername(username))
      return false;
    userRepository.save(new User(username));
    return true;
  }

  public Optional<User> loginUser(String username) {
    return userRepository.findByUsername(username);
  }
}
