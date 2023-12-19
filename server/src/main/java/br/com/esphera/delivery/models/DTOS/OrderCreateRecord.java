package br.com.esphera.delivery.models.DTOS;

import br.com.esphera.delivery.models.Enums.TypeDelivery;
import jakarta.validation.constraints.NotNull;

public record OrderCreateRecord(
        @NotNull(message = "O campo nome do cliente é obrigatório")
        String clientName,
        String cpf,
        @NotNull(message = "O campo número do contato é obrigatório")
        String numberCellphone,
        @NotNull(message = "O campo endereço é obrigatório")
        AddressRecord addressRecord,
        @NotNull(message = "O campo id do carrinho é obrigatório")
        Integer shoppingCartId,
        String couponName,
        @NotNull(message = "O campo tipo de entrega é obrigatório")
        TypeDelivery typeDelivery) {
}
