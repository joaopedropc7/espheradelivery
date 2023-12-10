package br.com.esphera.delivery.models.DTOS;

import br.com.esphera.delivery.models.Enums.UserRole;


public record RegisterDTO(
        String name,
        String email,
        String password,
        String numberCellphone,
        UserRole role,
        Integer companyId) {
}
