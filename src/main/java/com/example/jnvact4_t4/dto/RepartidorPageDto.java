package com.example.jnvact4_t4.dto;

import java.util.List;

public record RepartidorPageDto(
        List<RepartidorResponseDto> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
