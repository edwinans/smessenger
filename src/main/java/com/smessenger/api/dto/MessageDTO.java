package com.smessenger.api.dto;

import java.time.Instant;

public record MessageDTO(Long id, Instant createdAt, String senderUsername, String receiverUsername, String text) {
}
