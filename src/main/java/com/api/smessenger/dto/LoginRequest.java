package com.api.smessenger.dto;

public class LoginRequest {

  private String username;

  public LoginRequest(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
