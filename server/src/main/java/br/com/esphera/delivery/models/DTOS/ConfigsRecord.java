package br.com.esphera.delivery.models.DTOS;

public record ConfigsRecord(
        Double minimumOrder,
        String hourOpenEstablishment,
        String hourCloseEstablishment,
        Double maximumDistanceDelivery,
        Double valueKmDelivery,
        Integer maximumDiscountPercent
) {
}
