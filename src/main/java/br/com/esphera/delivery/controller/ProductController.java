package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.DTOS.ProductRecord;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductModel createProduct(@RequestBody ProductRecord dto){
        ProductModel productModel = productService.createProduct(dto);
        return productModel;
    }

    @GetMapping
    public List<ProductModel> findAllProducts(){
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ProductModel findProductById(@PathVariable(value = "id") Integer id){
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public ProductModel updateProduct(@PathVariable(value = "id")Integer id, @RequestBody ProductRecord productRecord){
        return productService.updateProduct(id, productRecord);
    }

    @DeleteMapping("/{id}")
    public void inactiveProduct(@PathVariable(value = "id")Integer id){
        productService.inactiveProduct(id);
    }


}
