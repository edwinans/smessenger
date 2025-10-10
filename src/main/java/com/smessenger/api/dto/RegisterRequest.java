package com.smessenger.api.dto;

public class RegisterRequest {
  private String username;
  // private String passwd;

  public RegisterRequest(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
