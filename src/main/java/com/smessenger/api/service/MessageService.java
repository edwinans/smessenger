package com.smessenger.api.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.smessenger.api.model.Message;
import com.smessenger.api.model.User;
import com.smessenger.api.repository.MessageRepository;
import com.smessenger.api.repository.UserRepository;
import com.smessenger.api.config.JwtUtil;
import com.smessenger.api.dto.MessageDTO;

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

  private MessageDTO toDto(Message m) {
    User sender = userRepository.findById(m.getSenderId())
        .orElseThrow(() -> new IllegalArgumentException("sender not found"));
    User receiver = userRepository.findById(m.getReceiverId())
        .orElseThrow(() -> new IllegalArgumentException("receiver not found"));
    return new MessageDTO(m.getId(), m.getCreatedAt(), sender.getUsername(), receiver.getUsername(), m.getText());
  }

  public MessageDTO sendMessageByUsername(String authorizationHeader, String receiverUsername, String text) {
    String senderUsername = JwtUtil.extractUsernameFromAuthorizationHeader(authorizationHeader);
    User sender = userRepository.findByUsername(senderUsername)
        .orElseThrow(() -> new IllegalArgumentException("sender not found"));
    User receiver = userRepository.findByUsername(receiverUsername)
        .orElseThrow(() -> new IllegalArgumentException("receiver not found"));
    Message saved = sendMessage(sender.getId(), receiver.getId(), text);
    return toDto(saved);
  }

  public List<MessageDTO> getRecentFromSender(String authorizationHeader, String senderUsername, Long beforeId,
      int limit) {
    User sender = userRepository.findByUsername(senderUsername)
        .orElseThrow(() -> new IllegalArgumentException("sender not found"));
    String receiverUsername = JwtUtil.extractUsernameFromAuthorizationHeader(authorizationHeader);
    User receiver = userRepository.findByUsername(receiverUsername)
        .orElseThrow(() -> new IllegalArgumentException("receiver not found"));
    var pageable = PageRequest.of(0, Math.max(1, limit));
    List<Message> rows;
    if (beforeId == null) {
      rows = messageRepository.findRecentBetween(sender.getId(), receiver.getId(), pageable);
    } else {
      rows = messageRepository.findRecentBetweenBefore(sender.getId(), receiver.getId(), beforeId, pageable);
    }
    return rows.stream().map(this::toDto).toList();
  }
}
