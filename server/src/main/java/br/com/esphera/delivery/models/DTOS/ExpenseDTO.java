package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotNull;

public record ExpenseDTO(
        @NotNull
        String nameExpense,
        @NotNull
        String datePayment,
        @NotNull
        Boolean recurringPayment,
        @NotNull
        Double valuePayment,
        @NotNull
        Integer companyId
) {
}
