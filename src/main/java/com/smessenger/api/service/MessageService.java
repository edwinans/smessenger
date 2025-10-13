package com.smessenger.api.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.smessenger.api.model.Message;
import com.smessenger.api.model.User;
import com.smessenger.api.repository.MessageRepository;
import com.smessenger.api.repository.UserRepository;

@Service
public class MessageService {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;

  public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
    this.messageRepository = messageRepository;
    this.userRepository = userRepository;
  }

  public Message sendMessage(Long senderId, Long receiverId, String text) {
    User receiver = userRepository.findById(receiverId)
        .orElseThrow(() -> new IllegalArgumentException("receiver not found"));
    // sender existence is assumed; you may validate if needed
    Message m = new Message(senderId, receiver.getId(), text);
    return messageRepository.save(m);
  }

  public Message sendMessageFromAuth(String authorizationHeader, Long receiverId, String text) {
    String username = com.smessenger.api.util.JwtUtil.extractUsernameFromAuthorizationHeader(authorizationHeader);
    User sender = userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("sender not found"));
    return sendMessage(sender.getId(), receiverId, text);
  }

  public List<Message> getRecent(Long senderId, Long receiverId, int count) {
    var pageable = PageRequest.of(0, Math.max(1, count));
    return messageRepository.findRecentBetween(senderId, receiverId, pageable);
  }

  public List<Message> getRecentForReceiverAuth(String authorizationHeader, Long senderId, int count) {
    String username = com.smessenger.api.util.JwtUtil.extractUsernameFromAuthorizationHeader(authorizationHeader);
    User receiver = userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("receiver not found"));
    return getRecent(senderId, receiver.getId(), count);
  }

  public List<Message> getSinceId(Long senderId, Long receiverId, Long sinceId) {
    return messageRepository.findSinceId(senderId, receiverId, sinceId);
  }

  public List<Message> getSinceForReceiverAuth(String authorizationHeader, Long senderId, Long sinceId) {
    String username = com.smessenger.api.util.JwtUtil.extractUsernameFromAuthorizationHeader(authorizationHeader);
    User receiver = userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("receiver not found"));
    return getSinceId(senderId, receiver.getId(), sinceId);
  }
}
