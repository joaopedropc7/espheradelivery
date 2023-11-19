package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.ProductEntryRecord;
import br.com.esphera.delivery.models.ProductEntryItemModel;
import br.com.esphera.delivery.models.ProductEntryModel;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.repository.ProductEntryItemModelRepository;
import br.com.esphera.delivery.repository.ProductEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductEntryService {

    @Autowired
    private ProductEntryRepository entryRepository;

    @Autowired
    private ProductEntryItemModelRepository productEntryItemModelRepository;

    @Autowired
    private ProductService productService;

    public ProductEntryModel createEntryProduct(ProductEntryRecord data){
        List<ProductModel> productsModel = new ArrayList<>();
        List<ProductEntryItemModel> productsEntryModel = new ArrayList<>();
        data.products().forEach(product -> {
            ProductModel productModel = productService.findById(product.idProduct());
            ProductEntryItemModel productEntryItemModel = new ProductEntryItemModel(productModel, product.quantity(), product.priceBuy());
            productsEntryModel.add(productEntryItemModel);
            productsModel.add(productModel);
        });
        ProductEntryModel entryModel = new ProductEntryModel(data, productsEntryModel);
        entryRepository.save(entryModel);
        productService.addQuantityInProduct(productsEntryModel);
        productsEntryModel.forEach(productEntryItemModel -> {
            productEntryItemModel.setEntryModel(entryModel);
            productEntryItemModelRepository.save(productEntryItemModel);
            });
        return entryModel;
    }

    public List<ProductEntryModel> findAll(){
        return entryRepository.findAll();
    }

    public ProductEntryModel findById(Integer id){
        ProductEntryModel productEntryModel = entryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado entradas com o id: " + id));
        return productEntryModel;
    }

    public void cancelEntryById(Integer id){
        ProductEntryModel productEntryModel = entryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado entradas com o id: " + id));
        productEntryModel.setEntryCanceled(true);
        productService.cancelEntryById(productEntryModel.getProducts());

    }

}
