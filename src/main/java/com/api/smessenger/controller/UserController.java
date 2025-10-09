package com.api.smessenger.controller;

import org.springframework.web.bind.annotation.RestController;

import com.api.smessenger.dto.RegisterReq;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

  @PostMapping("/register")
  public void register(@RequestBody RegisterReq req) {
    System.out.println(req.getUsername());
    // User user = userService.register(req.getUsername());
    return;
  }
}
