package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserRecord(
        @NotNull(message = "O campo nome é obrigatório")
        String name,
        String cpf,
        @Email
        @NotNull(message = "O campo email é obrigatório")
        String email,
        String numberCellphone) {
}
