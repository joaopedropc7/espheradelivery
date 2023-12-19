package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(
        @NotNull(message = "O campo email é obrigatório")
        String email,
        @NotNull(message = "O campo senha é obrigatório")
        String password
) {
}
