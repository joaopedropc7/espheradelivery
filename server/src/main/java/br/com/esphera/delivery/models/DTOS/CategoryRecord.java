package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotNull;

public record CategoryRecord(
        @NotNull(message = "O campo nome é obrigatório")
        String categoryName
) {
}
