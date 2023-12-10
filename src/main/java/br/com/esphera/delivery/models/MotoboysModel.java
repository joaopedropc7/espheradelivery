package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.MotoboyRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Entity
@Table(name = "motoboys")
public class MotoboysModel extends RepresentationModel<MotoboysModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nameMotoboy;
    private String number;
    private String email;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private CompanyModel companyModel;
    private Integer quantityDelivered;
    private Boolean inactive;

    public MotoboysModel() {
    }

    public MotoboysModel(MotoboyRecord dto, CompanyModel companyModel){
        this.nameMotoboy = dto.nameMotoboy();
        this.number = dto.number();
        this.email = dto.email();
        this.companyModel = companyModel;
        this.quantityDelivered = 0;
        this.inactive = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameMotoboy() {
        return nameMotoboy;
    }

    public void setNameMotoboy(String nameMotoboy) {
        this.nameMotoboy = nameMotoboy;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CompanyModel getCompanyModel() {
        return companyModel;
    }

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }

    public Integer getQuantityDelivered() {
        return quantityDelivered;
    }

    public void setQuantityDelivered(Integer quantityDelivered) {
        this.quantityDelivered = quantityDelivered;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotoboysModel that = (MotoboysModel) o;
        return Objects.equals(id, that.id) && Objects.equals(nameMotoboy, that.nameMotoboy) && Objects.equals(number, that.number) && Objects.equals(email, that.email) && Objects.equals(companyModel, that.companyModel) && Objects.equals(quantityDelivered, that.quantityDelivered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameMotoboy, number, email, companyModel, quantityDelivered);
    }
}
