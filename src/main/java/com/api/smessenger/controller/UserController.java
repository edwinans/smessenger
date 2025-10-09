package com.api.smessenger.controller;

import org.springframework.web.bind.annotation.RestController;

import com.api.smessenger.dto.BaseResponse;
import com.api.smessenger.dto.RegisterRequest;
import com.api.smessenger.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    boolean success = userService.registerUser(req.getUsername());

    BaseResponse response;
    HttpStatus status;

    if (success) {
      response = new BaseResponse("User '" + req.getUsername() + "' registered successfully!");
      status = HttpStatus.CREATED; // 201
    } else {
      response = new BaseResponse("Username '" + req.getUsername() + "' is already taken!");
      status = HttpStatus.CONFLICT; // 409
    }

    return new ResponseEntity<>(response, status);
  }

}
