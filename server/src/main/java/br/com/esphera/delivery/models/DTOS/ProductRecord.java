package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductRecord(
        @NotBlank(message = "O campo nome não pode ser nulo")
        String name,
        @NotBlank(message = "O campo categoria não pode ser nulo")
        Integer idCategory,
        @NotBlank(message = "O campo descrição não pode ser nulo")
        String description,
        Double costValue,
        @NotBlank(message = "O campo valor de venda não pode ser nulo")
        Double valueSell,
        List<Integer> optinalsId,
        Integer quantityOptionalsFree
        ) {
}
