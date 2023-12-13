package br.com.esphera.delivery.validations.entry;

import br.com.esphera.delivery.models.DTOS.ProductEntryRecord;

public interface ValidationEntry {

    void valid(ProductEntryRecord data);
}
