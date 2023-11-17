package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.ProductRecord;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductModel createProduct(ProductRecord dto){
        ProductModel product = new ProductModel(dto);
        productRepository.save(product);
        return product;
    }

    public List<ProductModel> findAllProducts(){
        List<ProductModel> products = productRepository.findAll();
        return products;
    }

    public ProductModel findById(Integer id){
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return product;
    }

    public ProductModel updateProduct(Integer id, ProductRecord dto){
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setImage(dto.image());
        product.setCostValue(dto.costValue());
        product.setValueSell(dto.valueSell());
        productRepository.save(product);
        return product;
    }

    public void inactiveProduct(Integer id){
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        product.setInactive(true);
        productRepository.save(product);
    }
}
