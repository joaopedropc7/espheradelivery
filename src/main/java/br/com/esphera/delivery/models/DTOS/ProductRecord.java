package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRecord(
        @NotBlank(message = "O campo nome não pode ser nulo")
        String name,
        @NotBlank(message = "O campo categoria não pode ser nulo")
        Integer idCategory,
        String description,
        Double costValue,
        Double valueSell) {
}
