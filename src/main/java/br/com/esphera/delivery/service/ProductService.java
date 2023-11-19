package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.ProductRecord;
import br.com.esphera.delivery.models.CategoryModel;
import br.com.esphera.delivery.models.ProductEntryItemModel;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.repository.CategoryRepository;
import br.com.esphera.delivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductModel createProduct(ProductRecord dto){
        CategoryModel category = categoryRepository.findById(dto.idCategory()).orElseThrow(() -> new ResourceNotFoundException("Não existe categoria com este ID!"));
        ProductModel product = new ProductModel(dto, category);
        productRepository.save(product);
        return product;
    }

    public List<ProductModel> findProductsByCategory(Integer categoryId){
        CategoryModel category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Não existe categoria com este ID!"));
        List<ProductModel> products = productRepository.findByCategoryId(category.getId());
        return products;
    }

    public List<ProductModel> findAllProducts(){
        List<ProductModel> products = productRepository.findAll();
        return products;
    }

    public ProductModel findById(Integer id){
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        return product;
    }

    public ProductModel updateProduct(Integer id, ProductRecord dto){
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        CategoryModel category = categoryRepository.findById(dto.idCategory()).orElseThrow(() -> new ResourceNotFoundException("Não existe categoria com este ID!"));

        product.setName(dto.name());
        product.setCategoryModel(category);
        product.setDescription(dto.description());
        product.setImage(dto.image());
        product.setCostValue(dto.costValue());
        product.setValueSell(dto.valueSell());
        productRepository.save(product);
        return product;
    }

    public void inactiveProduct(Integer id){
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        product.setInactive(true);
        productRepository.save(product);
    }

    public void activeProduct(Integer id){
        ProductModel product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        product.setInactive(false);
        productRepository.save(product);
    }

    public void sellProduct(Integer productId, Integer productQuantity){
        ProductModel product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        product.setQuantity(product.getQuantity() - productQuantity);
        product.setSales(product.getSales() + productQuantity);
        productRepository.save(product);
    }

    public void revertSellProduct(Integer productId, Integer productQuantity){
        ProductModel product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
        product.setQuantity(product.getQuantity() + productQuantity);
        product.setSales(product.getSales() - productQuantity);
        productRepository.save(product);
    }

    public void addQuantityInProduct(List<ProductEntryItemModel> productsList){
        productsList.forEach(product -> {
            ProductModel productModel = productRepository.findById(product.getProduct().getId()).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
            productModel.setQuantity(productModel.getQuantity() + product.getQuantity());
            productRepository.save(productModel);
        });
    }

    public void cancelEntryById(List<ProductEntryItemModel> products) {
        products.forEach(product -> {
            ProductModel productModel = productRepository.findById(product.getProduct().getId()).orElseThrow(() -> new ResourceNotFoundException("Não existe produto com este ID!"));
            productModel.setQuantity(productModel.getQuantity() - product.getQuantity());
            productRepository.save(productModel);
        });
    }
}
