package br.com.esphera.delivery.models.DTOS;

import java.util.List;

public record AddProductCartRecord(
        Integer productId,
        Integer productQuantity,
        List<Integer> optionalsId
) {
}
