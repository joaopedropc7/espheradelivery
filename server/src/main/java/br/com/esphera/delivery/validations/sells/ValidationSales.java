package br.com.esphera.delivery.validations.sells;

import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.ShoppingCartModel;

public interface ValidationSales {

    void valid(OrderCreateRecord data, ShoppingCartModel shoppingCartModel);
}
