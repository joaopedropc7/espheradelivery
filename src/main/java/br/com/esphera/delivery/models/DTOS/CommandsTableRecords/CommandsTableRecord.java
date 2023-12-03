package br.com.esphera.delivery.models.DTOS.CommandsTableRecords;

import br.com.esphera.delivery.models.Enums.PaymentsMethod;

public record CommandsTableRecord(
        Integer tableId,
        PaymentsMethod paymentsMethod,
        String observation
) {
}
