package br.com.esphera.delivery.models.DTOS;

import br.com.esphera.delivery.models.Enums.TypeDelivery;
import jakarta.validation.constraints.NotNull;

public record OrderCreateRecord(
        @NotNull
        String clientName,
        @NotNull
        String cpf,
        @NotNull
        String numberCellphone,
        @NotNull
        AddressRecord addressRecord,
        @NotNull
        Integer shoppingCartId,
        String couponName,
        TypeDelivery typeDelivery) {
}
