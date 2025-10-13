package com.smessenger.api.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "message")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "sender_id", nullable = false)
  private Long senderId;

  @Column(name = "receiver_id", nullable = false)
  private Long receiverId;

  @Column(name = "text", nullable = false, length = 2000)
  private String text;

  public Message() {
    this.createdAt = Instant.now();
  }

  public Message(Long senderId, Long receiverId, String text) {
    this();
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.text = text;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public Long getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(Long receiverId) {
    this.receiverId = receiverId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
