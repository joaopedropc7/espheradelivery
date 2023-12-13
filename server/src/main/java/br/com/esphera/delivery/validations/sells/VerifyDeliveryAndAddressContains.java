package br.com.esphera.delivery.validations.sells;

import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.Enums.TypeDelivery;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.validations.exception.ValidateException;
import org.springframework.stereotype.Component;

@Component
public class VerifyDeliveryAndAddressContains implements ValidationSales{


    @Override
    public void valid(OrderCreateRecord data, ShoppingCartModel shoppingCartModel) {
        if(data.typeDelivery() == TypeDelivery.DELIVERY){
            if (data.addressRecord() == null){
                throw new ValidateException("Endereço inválido");
            }
        }
    }
}
