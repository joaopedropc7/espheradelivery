package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.ProductEntryQuantityIdRecord;
import br.com.esphera.delivery.models.DTOS.ProductEntryRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product_entry_item")
public class ProductEntryItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private ProductModel product;
    private Integer quantity;
    private Double priceBuy;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JsonIgnore
    private ProductEntryModel entryModel;

    public ProductEntryItemModel() {
    }

    public ProductEntryItemModel(ProductModel productModel, Integer quantity, Double priceBuy) {
        this.product = productModel;
        this.quantity = quantity;
        this.priceBuy = priceBuy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(Double priceBuy) {
        this.priceBuy = priceBuy;
    }

    public ProductEntryModel getEntryModel() {
        return entryModel;
    }

    public void setEntryModel(ProductEntryModel entryModel) {
        this.entryModel = entryModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntryItemModel that = (ProductEntryItemModel) o;
        return Objects.equals(id, that.id) && Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity) && Objects.equals(priceBuy, that.priceBuy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, priceBuy);
    }
}
