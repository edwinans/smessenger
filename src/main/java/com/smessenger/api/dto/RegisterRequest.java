package com.smessenger.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Username should not be blank") String username,
    @NotBlank(message = "Password should not be blank") @Size(min = 4, max = 32, message = "Password must be between 4 and 32 characters") String password) {
}
