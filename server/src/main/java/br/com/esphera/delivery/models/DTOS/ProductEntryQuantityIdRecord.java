package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotNull;

public record ProductEntryQuantityIdRecord(
        @NotNull(message = "O campo id do produto não pode ser nulo")
        Integer idProduct,
        @NotNull(message = "O campo quantidade de produtos não pode ser nulo")
        Integer quantity,
        @NotNull(message = "O campo preço de compra não pode ser nulo")
        Double priceBuy
) {
}
