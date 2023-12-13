package br.com.esphera.delivery.validations.entry;

import br.com.esphera.delivery.models.DTOS.ProductEntryRecord;
import br.com.esphera.delivery.validations.exception.ValidateException;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class ValidationQuantity implements ValidationEntry{
    @Override
    public void valid(ProductEntryRecord data) {

        AtomicReference<Integer> totalProducts = new AtomicReference<>(0);

        data.products().forEach(products -> {
            totalProducts.updateAndGet(v -> v + products.quantity());
        });

        if(!totalProducts.get().equals(data.quantityAllProducts())){
            throw new ValidateException("A quantidade informada não está igual a quantidade total!");
        }
    }
}
