package br.com.esphera.delivery.models.DTOS;

import br.com.esphera.delivery.models.OrderModel;

public record DeliveryRecord(
        OrderModel orderModel,
        AddressRecord addressRecord
) {
}
