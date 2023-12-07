package br.com.esphera.delivery.models.DTOS;

public record RegisterDTO(
        String name,
        String email,
        String password,
        String numberCellphone,
        Integer companyId) {
}
