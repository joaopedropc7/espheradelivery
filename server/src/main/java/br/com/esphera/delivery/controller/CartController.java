package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.DTOS.AddProductCartRecord;
import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.DTOS.responseDtos.OrderResponseDTO;
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

    @PostMapping("/add")
    @Operation(summary = "Insert in cart", description = "Insert product in cart", tags = {"Cart"})
    public ResponseEntity<ProductCartItemModel> addToCart(@RequestBody AddProductCartRecord dto){
       return ResponseEntity.ok().body(shoppingCartService.addToCart(dto));
    }

    @PutMapping("/alter/{id}/{quantity}")
    @Operation(summary = "Alter product quantity in cart", description = "Insert product in cart", tags = {"Cart"})
    public ResponseEntity<ProductCartItemModel> alterProductInCart(@PathVariable(value = "id") Integer productID, @PathVariable(value = "quantity") Integer quantityAdd){
        return ResponseEntity.ok().body(shoppingCartService.alterQuantityProductInCart(productID, quantityAdd));
    }


    @PostMapping("/remove/{id}")
    @Operation(summary = "remove in cart", description = "remove product in cart", tags = {"Cart"})
    public ResponseEntity removeToCart(@PathVariable(value = "id") Integer productID){
        shoppingCartService.removeFromCart(productID);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout/{companyId}")
    @Operation(summary = "Checkout in cart and create order", description = "Checkout in cart and create order", tags = {"Cart"})
    public ResponseEntity<OrderResponseDTO> checkout(@RequestBody OrderCreateRecord orderCreateRecord, @PathVariable(value = "companyId")Integer companyId){
        OrderResponseDTO orderResponseDTO = shoppingCartService.createOrderWithCart(orderCreateRecord, companyId);
        return ResponseEntity.ok().body(orderResponseDTO);
    }

    @GetMapping("/quantity")
    @Operation(summary = "Get quantity products in cart", description = "Get quantitu products in cart", tags = {"Cart"})
    public ResponseEntity<Integer> getQuantityTotalInCart(){
        return ResponseEntity.ok().body(shoppingCartService.getQuantityTotalInCart());
    }

    @GetMapping("/value")
    @Operation(summary = "Total value in cart", description = "Total value in cart", tags = {"Cart"})
    public ResponseEntity<Double> getPriceTotalInCart(){
        return ResponseEntity.ok().body(shoppingCartService.getPriceTotalCart());
    }

    @GetMapping("/cartproducts")
    @Operation(summary = "Get products in cart", description = "Get products product in cart", tags = {"Cart"})
    public ResponseEntity<List<ProductCartItemModel>> productsInCart(){
        return ResponseEntity.ok().body(shoppingCartService.getProductsInCart());
    }

    @PostMapping("/clearcart")
    @Operation(summary = "Clear cart", description = "Clear cart", tags = {"Cart"})
    public ResponseEntity clearCart(){
        shoppingCartService.clearCart();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get cart by in DB", description = "Get cart by id in DB", tags = {"Cart"})
    public ResponseEntity<ShoppingCartModel> getShoppingCartById(@PathVariable(value = "id")Integer id){
        ShoppingCartModel shoppingCart = shoppingCartService.getShoppingCartInDBById(id);
        return ResponseEntity.ok().body(shoppingCart);
    }


}
