package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotNull;

public record CreateCouponDTO(
        @NotNull(message = "O campo nome é obrigatório")
        String name,
        @NotNull(message = "O campo percentual de desconto é obrigatório")
        Double percentDiscount,
        @NotNull(message = "O campo data de inicio do cupom é obrigatório")
        String startDate,
        @NotNull(message = "O campo data de expiração do cupom é obrigatório")
        String expireDate,
        @NotNull(message = "O campo limite de uso é obrigatório")
        Boolean limitUses,
        @NotNull(message = "O campo número de usos permitidos é obrigatório")
        Integer numberUsesAllowed
) {
}
