package com.smessenger.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.smessenger.api.service.UserService;

@Configuration
public class InitDB {
  private static final Logger log = LoggerFactory.getLogger(InitDB.class);

  @Bean
  public CommandLineRunner initUser(UserService userService) {
    return _ -> {
      userService.registerUser("test", "pass");
      log.info("User 'test' registered");
    };
  }
}
