package com.api.smessenger.model;

public class User {
  private String username;

  public User(String username) {
    this.username = username;
    System.out.println("Logged in: " + username);
  }

  public String getUsername() {
    return username;
  }

}
