package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.ProductCartItemModel;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.service.ProductService;
import br.com.esphera.delivery.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add/{id}/{quantity}")
    @Operation(summary = "Insert in cart", description = "Insert product in cart",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ShoppingCartModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ProductCartItemModel addToCart(@PathVariable(value = "id") Integer productID, @PathVariable(value = "quantity") Integer quantity){
       ProductModel productModel = productService.findById(productID);
       ProductCartItemModel productCartItemModel = new ProductCartItemModel(productModel, quantity);
       return shoppingCartService.addToCart(productCartItemModel);
    }

    @PostMapping("/remove/{id}")
    @Operation(summary = "remove in cart", description = "remove product in cart",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ShoppingCartModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity removeToCart(@PathVariable(value = "id") Integer productID){
        shoppingCartService.removeFromCart(productID);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout")
    @Operation(summary = "Checkout in cart", description = "Checkout in cart",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ShoppingCartModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Integer> checkout(){
        ShoppingCartModel shoppingCartModel = shoppingCartService.checkout();
        return ResponseEntity.ok(shoppingCartModel.getId());
    }

    @GetMapping("/quantity")
    @Operation(summary = "Get quantity products in cart", description = "Get quantitu products in cart",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ShoppingCartModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public Integer getQuantityTotalInCart(){
        return shoppingCartService.getQuantityTotalInCart();
    }

    @GetMapping("/value")
    @Operation(summary = "Total value in cart", description = "Total value in cart",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ShoppingCartModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public Double getPriceTotalInCart(){
        return shoppingCartService.getPriceTotalCart();
    }

    @GetMapping("/cartproducts")
    @Operation(summary = "Get products in cart", description = "Get products product in cart",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ShoppingCartModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public List<ProductCartItemModel> productsInCart(){
        return shoppingCartService.getProductsInCart();
    }

    @PostMapping("/clearcart")
    @Operation(summary = "Clear cart", description = "Clear cart",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ShoppingCartModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity clearCart(){
        shoppingCartService.clearCart();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get cart by in DB", description = "Get cart by id in DB",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ShoppingCartModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ShoppingCartModel getShoppingCartById(@PathVariable(value = "id")Integer id){
        ShoppingCartModel shoppingCart = shoppingCartService.getShoppingCartInDBById(id);
        return shoppingCart;
    }


}
