package br.com.esphera.delivery.models.DTOS;

import br.com.esphera.delivery.models.Enums.UserRole;
import jakarta.validation.constraints.NotNull;


public record RegisterDTO(
        @NotNull(message = "O campo nome é obrigatório")
        String name,
        @NotNull(message = "O campo email é obrigatório")
        String email,
        @NotNull(message = "O campo senha é obrigatório")
        String password,
        @NotNull(message = "O campo número do contato é obrigatório")
        String numberCellphone,
        @NotNull(message = "O campo endereço é obrigatório")
        UserRole role,
        @NotNull(message = "O campo id da empresa é obrigatório")
        Integer companyId) {
}
