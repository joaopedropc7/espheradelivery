package br.com.esphera.delivery.models.DTOS;

public record DeliveryConsultValueDTO(
        Double distance,
        Integer duration,
        Double value
) {
}
