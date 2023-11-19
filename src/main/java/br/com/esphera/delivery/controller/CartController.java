package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.ProductCartItemModel;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.service.ProductService;
import br.com.esphera.delivery.service.ShoppingCartService;
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
    public ProductCartItemModel addToCart(@PathVariable(value = "id") Integer productID, @PathVariable(value = "quantity") Integer quantity){
       ProductModel productModel = productService.findById(productID);
       ProductCartItemModel productCartItemModel = new ProductCartItemModel(productModel, quantity);
       return shoppingCartService.addToCart(productCartItemModel);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity removeToCart(@PathVariable(value = "id") Integer productID){
        shoppingCartService.removeFromCart(productID);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<Integer> checkout(){
        ShoppingCartModel shoppingCartModel = shoppingCartService.checkout();
        return ResponseEntity.ok(shoppingCartModel.getId());
    }

    @GetMapping("/quantity")
    public Integer getQuantityTotalInCart(){
        return shoppingCartService.getQuantityTotalInCart();
    }

    @GetMapping("/value")
    public Double getPriceTotalInCart(){
        return shoppingCartService.getPriceTotalCart();
    }

    @GetMapping("/cartproducts")
    public List<ProductCartItemModel> productsInCart(){
        return shoppingCartService.getProductsInCart();
    }

    @GetMapping("/clearcart")
    public ResponseEntity clearCart(){
        shoppingCartService.clearCart();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ShoppingCartModel getShoppingCartById(@PathVariable(value = "id")Integer id){
        ShoppingCartModel shoppingCart = shoppingCartService.getShoppingCartInDBById(id);
        return shoppingCart;
    }


}
