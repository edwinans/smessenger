package com.api.smessenger.dto;

public class RegisterReq {
  private String username;
  // private String passwd;

  public RegisterReq(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
