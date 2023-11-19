package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.ProductEntryRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product_entry")
public class ProductEntryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double totalValue;
    private String supplier;
    private LocalDate dateEntry;
    private Integer quantityAllProducts;
    @OneToMany(mappedBy = "entryModel", cascade = CascadeType.ALL)
    private List<ProductEntryItemModel> products;
    private Boolean entryCanceled;

    public ProductEntryModel() {
    }

    public ProductEntryModel(ProductEntryRecord data, List<ProductEntryItemModel> products) {
        this.totalValue = data.totalValue();
        this.supplier = data.supplier();
        this.dateEntry = LocalDate.parse(data.dateEntry());
        this.quantityAllProducts = data.quantityAllProducts();
        this.products = products;
        this.entryCanceled = false;
    }

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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public LocalDate getDateEntry() {
        return dateEntry;
    }

    public void setDateEntry(LocalDate dateEntry) {
        this.dateEntry = dateEntry;
    }

    public Integer getQuantityAllProducts() {
        return quantityAllProducts;
    }

    public void setQuantityAllProducts(Integer quantityAllProducts) {
        this.quantityAllProducts = quantityAllProducts;
    }

    public List<ProductEntryItemModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntryItemModel> products) {
        this.products = products;
    }

    public Boolean getEntryCanceled() {
        return entryCanceled;
    }

    public void setEntryCanceled(Boolean entryCanceled) {
        this.entryCanceled = entryCanceled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntryModel that = (ProductEntryModel) o;
        return Objects.equals(id, that.id) && Objects.equals(totalValue, that.totalValue) && Objects.equals(supplier, that.supplier) && Objects.equals(dateEntry, that.dateEntry) && Objects.equals(quantityAllProducts, that.quantityAllProducts) && Objects.equals(products, that.products) && Objects.equals(entryCanceled, that.entryCanceled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalValue, supplier, dateEntry, quantityAllProducts, products, entryCanceled);
    }
}
