package br.com.esphera.delivery.validations.sells;

import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.Enums.TypeDelivery;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.validations.exception.ValidateException;
import org.springframework.stereotype.Component;

@Component
public class VerifyProductInactive implements ValidationSales{


    @Override
    public void valid(OrderCreateRecord data, ShoppingCartModel shoppingCartModel) {
       shoppingCartModel.getProductCartItems().forEach(product -> {
           if(product.getProduct().getInactive() == Boolean.TRUE){
               throw new ValidateException("O produto " + product.getProduct().getName() + " está inativo!");
           }
       });
    }
}
