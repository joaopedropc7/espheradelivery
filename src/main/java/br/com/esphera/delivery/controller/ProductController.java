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
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "create product parsing body and idCompany", description = "create product parsing body and idCompany",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRecord dto, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        ProductModel productModel = productService.createProduct(dto, companyId);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(productModel);
        return ResponseEntity.ok().body(productResponseDTO);
    }

    @GetMapping("/find")
    @Operation(summary = "find products parsing idCompany", description = "find products parsing idCompany",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<ProductResponseDTO>> findAllProducts(HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        List<ProductModel> productModel = productService.findAllProducts(companyId);
        List<ProductResponseDTO> products = ProductResponseDTO.convert(productModel);
        return ResponseEntity.ok().body(products);
    }


    @GetMapping("/category/{categoryId}")
    @Operation(summary = "find products by category parsing categoryId and companyId", description = "find products by category parsing categoryId and companyId",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<ProductResponseDTO>> findAllProductsByCategory(HttpServletRequest request, @PathVariable(value = "categoryId")Integer categoryId){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        List<ProductModel> productModel = productService.findProductsByCategory(companyId, categoryId);
        List<ProductResponseDTO> products = ProductResponseDTO.convert(productModel);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "find products id", description = "find products by id",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ProductModel> findProductById(@PathVariable(value = "id") Integer id, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        return ResponseEntity.ok().body(productService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "put product by idproduct", description = "put product by idProduct",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ProductModel> updateProduct(@PathVariable(value = "id")Integer id, @RequestBody ProductRecord productRecord, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        return ResponseEntity.ok().body(productService.updateProduct(id, productRecord));
    }

    @PutMapping("/inactive/{productId}")
    @Operation(summary = "Inactive product by idproduct", description = "Inactive product by idProduct",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = void.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity inactiveProduct(@PathVariable(value = "productId")Integer productId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        productService.inactiveProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/active/{productId}")
    @Operation(summary = "Active product by idproduct", description = "Active product by idProduct",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = void.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity activeProduct(@PathVariable(value = "productId")Integer productId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        productService.activeProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product by idproduct", description = "Delete product by idProduct",
            tags = {"Product"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = void.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity deleteProduct(@PathVariable(value = "productId")Integer productId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
