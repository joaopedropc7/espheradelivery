package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.CategoryModel;
import br.com.esphera.delivery.models.DTOS.ProductRecord;
import br.com.esphera.delivery.models.DTOS.responseDtos.ProductResponseDTO;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "create product parsing body and idCompany", description = "create product parsing body and idCompany", tags = {"Product"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRecord dto, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        ProductModel productModel = productService.createProduct(dto, companyId);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(productModel);
        return ResponseEntity.ok().body(productResponseDTO);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "find products parsing idCompany", description = "find products parsing idCompany", tags = {"Product"})

    public ResponseEntity<Page<ProductResponseDTO>> findAllProducts(@PathVariable(value = "id")Integer id, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));
        Page<ProductModel> productModel = productService.findAllProducts(id, pageable);
        Page<ProductResponseDTO> products = ProductResponseDTO.convert(productModel);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/find")
    @Operation(summary = "find products parsing token", description = "find products parsing token", tags = {"Product"})
    public ResponseEntity<Page<ProductResponseDTO>> findAllProductsParsingToken(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        Integer companyId = tokenService.getCompanyIdFromToken(tokenService.recoverToken(request));
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));
        Page<ProductModel> productModel = productService.findAllProducts(companyId, pageable);
        Page<ProductResponseDTO> products = ProductResponseDTO.convert(productModel);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/findByName/{name}")
    @Operation(summary = "find products parsing name", description = "find products parsing name", tags = {"Product"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<ProductResponseDTO>> findAllProductsByName(@PathVariable(value = "name")String name,HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));
        Page<ProductModel> productModel = productService.findProductsByName(name ,companyId, pageable);
        Page<ProductResponseDTO> products = ProductResponseDTO.convert(productModel);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "find products by category parsing categoryId and companyId", description = "find products by category parsing categoryId and companyId", tags = {"Product"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<ProductResponseDTO>> findAllProductsByCategory(HttpServletRequest request, @PathVariable(value = "categoryId")Integer categoryId, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));
        Page<ProductModel> productModel = productService.findProductsByCategory(companyId, categoryId, pageable);
        Page<ProductResponseDTO> products = ProductResponseDTO.convert(productModel);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "find products id", description = "find products by id", tags = {"Product"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable(value = "id") Integer id){
        ProductModel productModel = productService.findById(id);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(productModel);
        return ResponseEntity.ok().body(productResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "put product by idproduct", description = "put product by idProduct", tags = {"Product"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable(value = "id")Integer id, @RequestBody ProductRecord productRecord, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        ProductModel productModel = productService.updateProduct(id, productRecord, companyId);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(productModel);
        return ResponseEntity.ok().body(productResponseDTO);
    }

    @PutMapping("/inactive/{productId}")
    @Operation(summary = "Inactive product by idproduct", description = "Inactive product by idProduct", tags = {"Product"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity inactiveProduct(@PathVariable(value = "productId")Integer productId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        productService.inactiveProduct(productId, companyId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/active/{productId}")
    @Operation(summary = "Active product by idproduct", description = "Active product by idProduct", tags = {"Product"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity activeProduct(@PathVariable(value = "productId")Integer productId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        productService.activeProduct(productId, companyId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product by idproduct", description = "Delete product by idProduct", tags = {"Product"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity deleteProduct(@PathVariable(value = "productId")Integer productId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        productService.deleteProduct(productId, companyId);
        return ResponseEntity.noContent().build();
    }


}
