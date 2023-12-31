package br.com.esphera.delivery.service;

import br.com.esphera.delivery.controller.CartController;
import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.*;
import br.com.esphera.delivery.models.DTOS.AddProductCartRecord;
import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.DTOS.responseDtos.OrderResponseDTO;
import br.com.esphera.delivery.repository.ShoppingCartRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {

    private static final String CART_ATTRIBUTE_NAME = "shoppingcart";

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OptionalService optionalService;

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

    public ProductCartItemModel addToCart(AddProductCartRecord dto){
        Double valueOptionals = 0.0;
        ProductModel productModel = productService.findById(dto.productId());
        ShoppingCartModel shoppingCartModel = getShoppingCartInSession();
        List<OptionalModel> optionals = new ArrayList<>();
        dto.optionalsId().forEach(optionalId -> {
            OptionalModel optionalModel = optionalService.getOptionalById(optionalId);
            optionals.add(optionalModel);
        });
        if (optionals.size() > productModel.getQuantityOptionalsFree()){
            for (int i = (productModel.getQuantityOptionalsFree() - 1); i <= optionals.size(); i++){
                valueOptionals += optionals.get(i).getPrice();
            }
        }
        ProductCartItemModel productCartItemModel = new ProductCartItemModel(productModel, dto.productQuantity(), valueOptionals);

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

    public OrderResponseDTO createOrderWithCart(OrderCreateRecord dto, Integer companyId){
        ShoppingCartModel shoppingCartModel = checkout();
        OrderModel orderModel = orderService.createSell(dto, shoppingCartModel, companyId);
        return new OrderResponseDTO(orderModel);
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
            shoppingCart.setTotalValue(shoppingCart.getTotalValue() + (productCart.getTotalValue()));
        }
    }

    public ShoppingCartModel getShoppingCartInDBById(Integer cartID){
        ShoppingCartModel shoppingCartModel = shoppingCartRepository.findById(cartID).orElseThrow(() -> new ResourceNotFoundException("Nenhum carrinho de compra encontrado ocm este id!"));
        shoppingCartModel.add(linkTo(methodOn(CartController.class).getShoppingCartById(shoppingCartModel.getId())).withSelfRel());
        shoppingCartModel.getProductCartItems().forEach(product -> {
            product.add(linkTo(methodOn(CartController.class).getShoppingCartById(shoppingCartModel.getId())).withSelfRel());
        });
        return shoppingCartModel;

    }

    public void clearCart(){
        this.httpSession.setAttribute(CART_ATTRIBUTE_NAME, new ShoppingCartModel());
    }
}
