package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.ProductRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "product")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel categoryModel;
    private String description;
    private String image;
    private Double costValue;
    private Double valueSell;
    private Integer quantity;
    private Integer sales;
    private LocalDate dateCreate;
    private Double valueBuyTotal;
    private Double valueSellTotal;
    private Boolean inactive;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private CompanyModel companyModel;


    public ProductModel() {
    }

    public ProductModel(ProductRecord dto, CategoryModel category, CompanyModel companyModel) {
        this.companyModel = companyModel;
        this.name = dto.name();
        this.categoryModel = category;
        this.description = dto.description();
        this.image = dto.image();
        this.costValue = dto.costValue();
        this.valueSell = dto.valueSell();
        this.valueBuyTotal = 0.0;
        this.valueSellTotal = 0.0;
        this.quantity = 0;
        this.sales = 0;
        this.dateCreate = LocalDate.now();
        this.inactive = false;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getCostValue() {
        return costValue;
    }

    public void setCostValue(Double costValue) {
        this.costValue = costValue;
    }

    public Double getValueSell() {
        return valueSell;
    }

    public void setValueSell(Double valueSell) {
        this.valueSell = valueSell;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public Double getValueBuyTotal() {
        return valueBuyTotal;
    }

    public void setValueBuyTotal(Double valueBuyTotal) {
        this.valueBuyTotal = valueBuyTotal;
    }

    public Double getValueSellTotal() {
        return valueSellTotal;
    }

    public void setValueSellTotal(Double valueSellTotal) {
        this.valueSellTotal = valueSellTotal;
    }

    public CompanyModel getCompanyModel() {
        return companyModel;
    }

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel productModel = (ProductModel) o;
        return Objects.equals(id, productModel.id) && Objects.equals(name, productModel.name) && Objects.equals(description, productModel.description) && Objects.equals(image, productModel.image) && Objects.equals(costValue, productModel.costValue) && Objects.equals(valueSell, productModel.valueSell) && Objects.equals(quantity, productModel.quantity) && Objects.equals(sales, productModel.sales) && Objects.equals(dateCreate, productModel.dateCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image, costValue, valueSell, quantity, sales, dateCreate);
    }
}
