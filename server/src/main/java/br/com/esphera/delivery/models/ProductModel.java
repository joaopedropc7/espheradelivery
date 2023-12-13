package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.ProductRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "product")
public class ProductModel extends RepresentationModel<ProductModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel categoryModel;
    private String description;
    @OneToOne
    @JoinColumn(name = "image_id")
    private FileEntity image;
    private Double costValue;
    private Double valueSell;
    private Integer quantity;
    private Integer sales;
    private LocalDate dateCreate;
    private Double valueBuyTotal;
    private Double valueSellTotal;
    private Boolean inactive;
    private LocalDate dateCreated;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private CompanyModel companyModel;


    public ProductModel() {
    }

    public ProductModel(ProductRecord dto, CategoryModel category, CompanyModel companyModel ) {
        this.companyModel = companyModel;
        this.name = dto.name();
        this.categoryModel = category;
        this.description = dto.description();
        this.costValue = dto.costValue();
        this.valueSell = dto.valueSell();
        this.valueBuyTotal = 0.0;
        this.valueSellTotal = 0.0;
        this.quantity = 0;
        this.sales = 0;
        this.dateCreate = LocalDate.now();
        this.inactive = false;
        this.dateCreated = LocalDate.now();
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

    public FileEntity getImage() {
        return image;
    }

    public void setImage(FileEntity image) {
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

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductModel that = (ProductModel) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(categoryModel, that.categoryModel) && Objects.equals(description, that.description) && Objects.equals(image, that.image) && Objects.equals(costValue, that.costValue) && Objects.equals(valueSell, that.valueSell) && Objects.equals(quantity, that.quantity) && Objects.equals(sales, that.sales) && Objects.equals(dateCreate, that.dateCreate) && Objects.equals(valueBuyTotal, that.valueBuyTotal) && Objects.equals(valueSellTotal, that.valueSellTotal) && Objects.equals(inactive, that.inactive) && Objects.equals(companyModel, that.companyModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, categoryModel, description, image, costValue, valueSell, quantity, sales, dateCreate, valueBuyTotal, valueSellTotal, inactive, companyModel);
    }
}
