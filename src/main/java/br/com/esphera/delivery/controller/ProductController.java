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

    @PostMapping("/{companyId}")
    public ProductModel createProduct(@RequestBody ProductRecord dto, @PathVariable(value = "companyId")Integer companyId){
        ProductModel productModel = productService.createProduct(dto, companyId);
        return productModel;
    }

    @GetMapping("/find/{companyId}")
    public List<ProductModel> findAllProducts(@PathVariable(value = "companyId")Integer companyId){
        return productService.findAllProducts(companyId);
    }

    @GetMapping("/category/{companyId}/{categoryId}")
    public List<ProductModel> findAllProductsByCategory(@PathVariable(value = "companyId")Integer companyId, @PathVariable(value = "categoryId")Integer categoryId){
        return productService.findProductsByCategory(companyId, categoryId);
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
