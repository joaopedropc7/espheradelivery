package br.com.esphera.delivery.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product_table")
public class ProductTableModel extends RepresentationModel<ProductTableModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "table_id")
    private TableModel tableModel;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private ProductModel product;
    private Integer quantity;
    private Double totalValue;
    @ManyToMany
    private List<OptionalModel> optionalModels;

    public ProductTableModel() {
    }

    public ProductTableModel(ProductModel product, Integer quantity, Double valueOptionals) {
        this.product = product;
        this.quantity = quantity;
        this.totalValue = product.getValueSell() + valueOptionals;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
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

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public List<OptionalModel> getOptionalModels() {
        return optionalModels;
    }

    public void setOptionalModels(List<OptionalModel> optionalModels) {
        this.optionalModels = optionalModels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTableModel that = (ProductTableModel) o;
        return Objects.equals(id, that.id) && Objects.equals(tableModel, that.tableModel) && Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tableModel, product, quantity);
    }
}
