package com.example.jnvact4_t4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RepartidorCreateDto(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,
        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100, message = "El apellido no puede superar 100 caracteres")
        String apellido,
        @NotBlank(message = "La dirección es obligatoria")
        @Size(max = 255, message = "La dirección no puede superar 255 caracteres")
        String direccion,
        @NotNull(message = "El número de ruta es obligatorio")
        @Positive(message = "El número de ruta debe ser mayor que cero")
        Integer numero_ruta
) {
}
