package com.smessenger.api.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.smessenger.api.dto.UserDTO;

import io.vavr.control.Either;

@Service
public class UserService {
  private Map<String, Long> usernameIDs; // TODO: move to repository
  private Collection<String> loggedInUsers;
  private AtomicLong index;

  public UserService() {
    usernameIDs = new ConcurrentHashMap<>();
    loggedInUsers = new HashSet<>();
    index = new AtomicLong();
  }

  public boolean registerUser(String username) {
    if (userExists(username)) {
      return false;
    } else {
      usernameIDs.put(username, index.incrementAndGet());
      return true;
    }
  }

  public Either<String, UserDTO> loginUser(String username) {
    if (userExists(username)) {
      if (loggedInUsers.contains(username)) {
        return Either.left("User already logged in");
      } else {
        loggedInUsers.add(username);
        return Either.right(new UserDTO(username));
      }
    } else {
      return Either.left("User not found");
    }
  }

  public boolean userExists(String username) {
    return usernameIDs.containsKey(username);
  }

  public Long getUserID(String username) {
    return usernameIDs.get(username);
  }

}
