package com.api.smessenger.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private ConcurrentHashMap<String, Long> userIDs; // TODO: move to repository
  private AtomicLong index;

  public UserService() {
    userIDs = new ConcurrentHashMap<>();
    index = new AtomicLong();
  }

  public boolean registerUser(String username) {
    if (userExists(username)) {
      return false;
    } else {
      userIDs.put(username, index.incrementAndGet());
      return true;
    }
  }

  public boolean userExists(String username) {
    return userIDs.containsKey(username);
  }

  public Long getUserID(String username) {
    return userIDs.get(username);
  }

}
