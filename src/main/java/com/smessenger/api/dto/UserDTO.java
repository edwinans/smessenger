package com.smessenger.api.dto;

// TODO: convert to Java record

public class UserDTO {
  private String username;

  public UserDTO(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
