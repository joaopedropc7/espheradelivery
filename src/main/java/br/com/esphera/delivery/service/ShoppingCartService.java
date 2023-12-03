package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.ProductCartItemModel;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.repository.ShoppingCartRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    private static final String CART_ATTRIBUTE_NAME = "shoppingcart";

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private HttpSession httpSession;

    private ShoppingCartModel getShoppingCartInSession() {
        ShoppingCartModel shoppingCart = (ShoppingCartModel) this.httpSession.getAttribute(CART_ATTRIBUTE_NAME);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCartModel();
            this.httpSession.setAttribute(CART_ATTRIBUTE_NAME, shoppingCart);
        }
        return shoppingCart;
    }

    public ProductCartItemModel alterQuantityProductInCart(Integer productCartId, Integer quantityAdd){
        ShoppingCartModel shoppingCartModel = getShoppingCartInSession();
        ProductCartItemModel productInCart = shoppingCartModel.alterQuantityProductInCart(productCartId, quantityAdd);
        calculateAmount();
        return productInCart;
    }

    public ProductCartItemModel addToCart(ProductCartItemModel productCartItemModel){
        ShoppingCartModel shoppingCartModel = getShoppingCartInSession();
        ProductCartItemModel producInCart = shoppingCartModel.addToCart(productCartItemModel);
        calculateAmount();
        return producInCart;
    }

    public void removeFromCart(Integer productId){
        ShoppingCartModel shoppingCartModel = getShoppingCartInSession();
        shoppingCartModel.removeProductFromCart(productId);
        calculateAmount();
    }

    public ShoppingCartModel checkout(){
        ShoppingCartModel shoppingCartModel = getShoppingCartInSession();
        shoppingCartModel.getProductCartItems().stream().forEach(p -> p.setShoppingCart(shoppingCartModel));
        return shoppingCartRepository.save(shoppingCartModel);
    }

    public Integer getQuantityTotalInCart(){
        ShoppingCartModel shoppingCart = getShoppingCartInSession();
        List<ProductCartItemModel> productsInCart = shoppingCart.getProductCartItems();
        Integer productsInCartQuantity = 0;
        if (productsInCart != null){
            for(ProductCartItemModel product : productsInCart){
                productsInCartQuantity += product.getQuantity();
            }
        }
        return productsInCartQuantity;
    }

    public Double getPriceTotalCart(){
        ShoppingCartModel shoppingCart = getShoppingCartInSession();
        return shoppingCart.getTotalValue();
    }

    public List<ProductCartItemModel> getProductsInCart(){
        ShoppingCartModel shoppingCart = getShoppingCartInSession();
        return shoppingCart.getProductCartItems();
    }

    public void calculateAmount(){
        ShoppingCartModel shoppingCart = getShoppingCartInSession();
        shoppingCart.setTotalValue((double) 0);
        for (ProductCartItemModel productCart : shoppingCart.getProductCartItems()){
            shoppingCart.setTotalValue(shoppingCart.getTotalValue() + (productCart.getProduct().getValueSell() * productCart.getQuantity()));
        }
    }

    public ShoppingCartModel getShoppingCartInDBById(Integer cartID){
        return shoppingCartRepository.findById(cartID).orElseThrow(() -> new ResourceNotFoundException("Nenhum carrinho de compra encontrado ocm este id!"));
    }

    public void clearCart(){
        this.httpSession.setAttribute(CART_ATTRIBUTE_NAME, new ShoppingCartModel());
    }
}
