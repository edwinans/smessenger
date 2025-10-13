package com.smessenger.api.dto;

public record SendMessageRequest(Long receiverId, String text) {
}
