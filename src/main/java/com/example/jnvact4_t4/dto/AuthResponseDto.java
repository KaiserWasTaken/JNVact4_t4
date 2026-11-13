package com.example.jnvact4_t4.dto;

public record AuthResponseDto(
        String token,
        String token_type,
        long expires_in
) {
}
