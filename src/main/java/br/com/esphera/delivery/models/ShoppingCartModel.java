package br.com.esphera.delivery.models;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shoppingcart")
public class ShoppingCartModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double totalValue;
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    private List<ProductCartItemModel> productCartItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public List<ProductCartItemModel> getProductCartItems() {
        return productCartItems;
    }

    public void setProductCartItems(List<ProductCartItemModel> productCartItems) {
        this.productCartItems = productCartItems;
    }

    public ProductCartItemModel addToCart(ProductCartItemModel productCartItemModel){
        if(productCartItems != null && productCartItems.contains(productCartItemModel)){
            ProductCartItemModel oldProduct =
                    productCartItems.stream()
                            .filter(s -> s.getProduct().getId() == productCartItemModel.getProduct().getId())
                            .findFirst().orElse(null);

            oldProduct.setQuantity(oldProduct.getQuantity() + productCartItemModel.getQuantity());
            return oldProduct;
        }
        else{
            if(productCartItems == null){
                productCartItems = new ArrayList<>();
            }
            productCartItems.add(productCartItemModel);
            return productCartItemModel;
        }
    }

    public void removeProductFromCart(Integer productId){
        ProductCartItemModel productCartItemModel =
                productCartItems.stream()
                        .filter(s -> s.getProduct().getId() == productId)
                        .findFirst().orElse(null);
        if (productCartItemModel != null){
            productCartItems.remove(productCartItemModel);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartModel that = (ShoppingCartModel) o;
        return Objects.equals(id, that.id) && Objects.equals(totalValue, that.totalValue) && Objects.equals(productCartItems, that.productCartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalValue, productCartItems);
    }
}
