package com.smessenger.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.smessenger.api.service.MessageService;
import com.smessenger.api.service.UserService;

@Configuration
public class InitDB {

  private final MessageService messageService;
  private static final Logger log = LoggerFactory.getLogger(InitDB.class);

  InitDB(MessageService messageService) {
    this.messageService = messageService;
  }

  @Bean
  public CommandLineRunner initUser(UserService userService) {
    return _ -> {
      var u1 = userService.registerUser("user1", "pass");
      var u2 = userService.registerUser("user2", "pass");

      for (int i = 1; i <= 20; i++)
        messageService.sendMessage(u2.getId(), u1.getId(),
            String.format("Hello (%d) from user2 to user1", i));

      log.info("User 'user1' and 'user2' registered");
    };
  }
}
