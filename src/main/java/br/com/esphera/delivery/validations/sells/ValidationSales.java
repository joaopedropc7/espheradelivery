package br.com.esphera.delivery.validations.sells;

import br.com.esphera.delivery.models.DTOS.SellCreateRecord;
import br.com.esphera.delivery.models.ShoppingCartModel;

public interface ValidationSales {

    void valid(SellCreateRecord data, ShoppingCartModel shoppingCartModel);
}
