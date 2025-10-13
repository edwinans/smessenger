package com.smessenger.api.dto;

public record SendMessageRequest(String receiverUsername, String text) {
}
