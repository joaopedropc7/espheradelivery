package br.com.esphera.delivery.validations.product;

import br.com.esphera.delivery.models.DTOS.ProductRecord;

public class ValidateOptionals implements ProductCreateValidate{
    @Override
    public void valid(ProductRecord data) {
        Integer sizeOptionals = data.optinalsId().size();
    }
}
