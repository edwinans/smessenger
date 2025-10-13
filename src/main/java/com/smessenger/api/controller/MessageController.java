package com.smessenger.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.smessenger.api.dto.ApiResponse;
import com.smessenger.api.dto.SendMessageRequest;
import com.smessenger.api.dto.GetRecentRequest;
import com.smessenger.api.dto.GetSinceRequest;
import com.smessenger.api.model.Message;
import com.smessenger.api.service.MessageService;

@RestController
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  // POST /messages/send { receiverId, text } (sender from Authorization: Bearer
  // <jwt>)
  @PostMapping("/messages/send")
  public ResponseEntity<ApiResponse<Message>> sendMessageJson(
      @RequestHeader(name = "Authorization", required = false) String authorization,
      @RequestBody SendMessageRequest req) {
    Message created = messageService.sendMessageFromAuth(authorization, req.receiverId(), req.text());
    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(created, null));
  }

  // POST /messages/get_recent { senderId, count } (receiver from Authorization)
  @PostMapping("/messages/get_recent")
  public ResponseEntity<ApiResponse<List<Message>>> getRecentJson(
      @RequestHeader(name = "Authorization", required = false) String authorization,
      @RequestBody GetRecentRequest req) {
    int count = req.count() == null ? 5 : req.count();
    List<Message> recent = messageService.getRecentForReceiverAuth(authorization, req.senderId(), count);
    return ResponseEntity.ok(new ApiResponse<>(recent, null));
  }

  // POST /messages/get_since { senderId, sinceId } (receiver from Authorization)
  @PostMapping("/messages/get_since")
  public ResponseEntity<ApiResponse<List<Message>>> getSinceJson(
      @RequestHeader(name = "Authorization", required = false) String authorization,
      @RequestBody GetSinceRequest req) {
    List<Message> msgs = messageService.getSinceForReceiverAuth(authorization, req.senderId(), req.sinceId());
    return ResponseEntity.ok(new ApiResponse<>(msgs, null));
  }

}
