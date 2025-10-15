package com.smessenger.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
    @Schema(example = "user1") String username,
    @Schema(example = "pass") String password) {
}
