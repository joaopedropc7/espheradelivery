package br.com.esphera.delivery.validations.sells;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductQuantityValidation implements ValidationSales{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void valid(OrderCreateRecord data, ShoppingCartModel shoppingCartModel){
        shoppingCartModel.getProductCartItems().forEach(productCartItemModel -> {
            if(productCartItemModel.getQuantity() > productRepository.findById(productCartItemModel.getProduct().getId()).get().getQuantity()){
                throw new ResourceNotFoundException("Quantidade de produto indisponível");
            }
        });
    }

}
