package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductEntryRecord(
        @NotNull(message = "O campo valor total não pode ser nulo")
        Double totalValue,
        @NotNull(message = "O campo fornecedor não pode ser nulo")
        String supplier,
        @NotNull(message = "O campo data de entrada não pode ser nulo")
        String dateEntry,
        @NotNull(message = "O campo quantidade de produtos não pode ser nulo")
        Integer quantityAllProducts,
        @NotNull(message = "O campo produtos não pode ser nulo")
        List<ProductEntryQuantityIdRecord> products
) {
}
