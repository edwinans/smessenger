package com.smessenger.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.smessenger.api.dto.ApiResponse;
import com.smessenger.api.dto.LoginRequest;
import com.smessenger.api.dto.RegisterRequest;
import com.smessenger.api.dto.TokenDTO;
import com.smessenger.api.model.User;
import com.smessenger.api.exception.CustomException;
import com.smessenger.api.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest req) {
    userService.registerUser(req.username(), req.password());
    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<Void>(null, null));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<TokenDTO>> login(@RequestBody LoginRequest req) {
    String token = userService.authenticateAndGetToken(req.username(), req.password());
    return ResponseEntity.ok(new ApiResponse<TokenDTO>(new TokenDTO(token), null));
  }

  @GetMapping("/list_users")
  public ResponseEntity<ApiResponse<Iterable<User>>> listUsers() {
    var users = userService.listAllUsers();
    return ResponseEntity.ok(new ApiResponse<Iterable<User>>(users, null));
  }

  @GetMapping("/test_exp")
  public String textExp() {
    throw new CustomException("Custom exception test");
  }

}
