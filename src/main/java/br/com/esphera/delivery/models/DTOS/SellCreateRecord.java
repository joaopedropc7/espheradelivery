package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotNull;

public record SellCreateRecord(@NotNull String clientName,@NotNull String cpf,@NotNull String numberCellphone, @NotNull EnderecoRecord enderecoRecord,@NotNull Integer shoppingCartId, Double discount) {
}
