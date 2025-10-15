package com.smessenger.api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.smessenger.api.dto.SendMessageRequest;
import com.smessenger.api.dto.MessageDTO;
import com.smessenger.api.service.MessageService;

@RestController
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  // POST /messages { receiver_username, text } (sender from Authorization)
  @PostMapping("/messages")
  public ResponseEntity<MessageDTO> sendMessage(
      @RequestHeader(name = "Authorization", required = false) String authorization,
      @RequestBody SendMessageRequest req) {
    MessageDTO created = messageService.sendMessageByUsername(authorization, req.receiverUsername(), req.text());
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  // GET /messages/from/{senderUsername}?before_id=&limit=
  @GetMapping("/messages/from/{sender_username}")
  public ResponseEntity<List<MessageDTO>> getRecentFromSender(
      @RequestHeader(name = "Authorization", required = false) String authorization,
      @PathVariable String sender_username,
      @RequestParam(name = "before_id", required = false) Long beforeId,
      @RequestParam(name = "limit", required = false, defaultValue = "5") int limit) {
    List<MessageDTO> msgs = messageService.getRecentFromSender(authorization, sender_username, beforeId, limit);
    return ResponseEntity.ok(msgs);
  }

}
