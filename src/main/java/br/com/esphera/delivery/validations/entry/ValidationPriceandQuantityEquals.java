package br.com.esphera.delivery.validations.entry;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.ProductEntryRecord;
import br.com.esphera.delivery.validations.exception.ValidateException;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class ValidationPriceandQuantityEquals implements ValidationEntry{
    @Override
    public void valid(ProductEntryRecord data) {

        AtomicReference<Double> valueTotalProducts = new AtomicReference<>(0.0);
        double tolerance = 0.01;

        data.products().forEach(product -> {
           valueTotalProducts.updateAndGet(v -> v + product.priceBuy() * product.quantity());
        });



        if(Math.abs(valueTotalProducts.get() - data.totalValue()) > tolerance){
            throw new ValidateException("O valor informado do valor total da mercadoria difere do valor somado dos produtos, verifique por gentileza!");
        }
    }
}
