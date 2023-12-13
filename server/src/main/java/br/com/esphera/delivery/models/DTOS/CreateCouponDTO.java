package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotNull;

public record CreateCouponDTO(
        @NotNull
        String name,
        @NotNull
        Double percentDiscount,
        @NotNull
        String startDate,
        @NotNull
        String expireDate,
        @NotNull
        Boolean limitUses,
        @NotNull
        Integer numberUsesAllowed
) {
}
