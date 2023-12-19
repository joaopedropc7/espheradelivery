package br.com.esphera.delivery.validations.product;

import br.com.esphera.delivery.models.DTOS.ProductEntryRecord;
import br.com.esphera.delivery.models.DTOS.ProductRecord;

public interface ProductCreateValidate {

    void valid(ProductRecord data);
}
