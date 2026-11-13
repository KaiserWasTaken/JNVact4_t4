package com.example.jnvact4_t4.dto;

public record RepartidorResponseDto(
        Long repartidor_id,
        String nombre,
        String apellido,
        String direccion,
        Integer numero_ruta
) {
}
