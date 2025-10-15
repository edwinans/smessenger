package com.smessenger.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.smessenger.api.dto.LoginRequest;
import com.smessenger.api.dto.RegisterRequest;
import com.smessenger.api.dto.TokenDTO;
import com.smessenger.api.dto.UserDTO;
import com.smessenger.api.exception.CustomException;
import com.smessenger.api.service.UserService;

import jakarta.validation.Valid;

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
  public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest req) {
    UserDTO createdUser = userService.registerUser(req.username(), req.password());
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest req) {
    String token = userService.authenticateAndGetToken(req.username(), req.password());
    return ResponseEntity.ok((new TokenDTO(token)));
  }

  @GetMapping("/list_users")
  public ResponseEntity<Iterable<UserDTO>> listUsers() {
    var users = userService.listAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/test_exception")
  public String textExp() {
    throw new CustomException("Custom exception test");
  }

}
