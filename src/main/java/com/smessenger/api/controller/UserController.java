package com.smessenger.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.smessenger.api.dto.ApiResponse;
import com.smessenger.api.dto.LoginRequest;
import com.smessenger.api.dto.RegisterRequest;
import com.smessenger.api.dto.TokenDTO;
import com.smessenger.api.model.User;
import com.smessenger.api.util.JwtUtil;
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
    boolean success = userService.registerUser(req.username(), req.password());
    if (success) {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(new ApiResponse<>(true, null, null));
    } else {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new ApiResponse<>(false, null, "Username is already taken"));
    }
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<TokenDTO>> login(@RequestBody LoginRequest req) {
    var userOpt = userService.loginUser(req.username(), req.password());
    if (userOpt.isPresent()) {
      String token = JwtUtil.generateToken(req.username());
      return ResponseEntity.ok(new ApiResponse<>(true, new TokenDTO(token), null));
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ApiResponse<>(false, null, "Invalid username or password"));
    }
  }

  @GetMapping("list_users")
  public ResponseEntity<ApiResponse<Iterable<User>>> listUsers() {
    var users = userService.listAllUsers();
    return ResponseEntity.ok(new ApiResponse<>(true, users, null));
  }

}
