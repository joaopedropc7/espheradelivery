package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.OptionalDTO;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "optional_products")
public class OptionalModel extends RepresentationModel<OptionalModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String image;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyModel companyModel;

    public OptionalModel() {
    }

    public OptionalModel(OptionalDTO optionalDTO, CompanyModel companyModel) {
        this.name = optionalDTO.name();
        this.price = optionalDTO.price();
        this.description = optionalDTO.description();
        this.companyModel = companyModel;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public CompanyModel getCompanyModel() {
        return companyModel;
    }

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }
}
