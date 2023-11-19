package br.com.esphera.delivery.validations.sells;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.SellCreateRecord;
import br.com.esphera.delivery.models.ShoppingCartModel;
import org.springframework.stereotype.Component;

@Component
public class CartQuantityValidation implements ValidationSales{

    @Override
    public void valid(SellCreateRecord data, ShoppingCartModel shoppingCartModel){
        if(shoppingCartModel.getProductCartItems().size() == 0){
            throw new ResourceNotFoundException("O carrinho está vazio");
        }
    }

}
