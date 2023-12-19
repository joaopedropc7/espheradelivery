package br.com.esphera.delivery.models.DTOS.responseDtos;

public record DeliveryConsultValueDTO(
        Double distance,
        Integer duration,
        Double value
) {
}
