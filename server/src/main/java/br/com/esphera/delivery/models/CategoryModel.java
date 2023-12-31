package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.CategoryRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categoryproduct")
public class CategoryModel extends RepresentationModel<CategoryModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String categoryName;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private CompanyModel companyModel;
    private Boolean inactive;

    public CategoryModel() {
    }

    public CategoryModel(CategoryRecord categoryRecord, CompanyModel companyModel ) {
        this.categoryName = categoryRecord.categoryName();
        this.companyModel = companyModel;
        this.inactive = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
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
        CategoryModel that = (CategoryModel) o;
        return Objects.equals(id, that.id) && Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName);
    }
}
