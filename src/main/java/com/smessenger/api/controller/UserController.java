package com.smessenger.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.smessenger.api.dto.BaseResponse;
import com.smessenger.api.dto.RegisterRequest;
import com.smessenger.api.model.User;
import com.smessenger.api.dto.LoginRequest;
import com.smessenger.api.dto.DataResponse;
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
  public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequest req) {
    boolean success = userService.registerUser(req.getUsername(), req.getPassword());

    BaseResponse response;
    HttpStatus status;

    if (success) {
      response = new BaseResponse("User '" + req.getUsername() + "' registered successfully!");
      status = HttpStatus.CREATED;
    } else {
      response = new BaseResponse("Username '" + req.getUsername() + "' is already taken!");
      status = HttpStatus.CONFLICT;
    }

    return new ResponseEntity<>(response, status);
  }

  @PostMapping("/login")
  public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest req) {
    var userOpt = userService.loginUser(req.getUsername(), req.getPassword());
    if (userOpt.isPresent()) {
      return ResponseEntity.ok(new DataResponse<>("Login successful!", userOpt.get()));
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new BaseResponse("Invalid username or password."));
    }

  }

  @GetMapping("list_users")
  public Iterable<User> listUsers() {
    var users = userService.listAllUsers();
    return users;
  }

}
