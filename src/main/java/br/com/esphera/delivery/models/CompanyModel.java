package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.CompanyRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "company")
public class CompanyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeFantasia;
    private String razaoSocial;
    private String numberCompany1;
    private String NumberCompany2;
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private EnderecoModel enderecoModel;
    private String emailCompany;
    private Boolean defaulter;
    @OneToMany
    @JoinColumn(name = "sells")
    @JsonIgnore
    private List<OrderModel> sells;
    @OneToMany
    @JoinColumn(name = "products")
    @JsonIgnore
    private List<ProductModel> products;
    private Double valueGenerated;
    private Boolean inactive;

    public CompanyModel() {
    }

    public CompanyModel(CompanyRecord dto) {
        this.nomeFantasia = dto.nomeFantasia();
        this.razaoSocial = dto.razaoSocial();
        this.numberCompany1 = dto.numberCompany1();
        this.NumberCompany2 = dto.numberCompany2();
        this.emailCompany = dto.emailCompany();
        this.enderecoModel = new EnderecoModel(dto.addressRecord());
        this.defaulter = false;
        this.valueGenerated = 0.0;
        this.inactive = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNumberCompany1() {
        return numberCompany1;
    }

    public void setNumberCompany1(String numberCompany1) {
        this.numberCompany1 = numberCompany1;
    }

    public String getNumberCompany2() {
        return NumberCompany2;
    }

    public void setNumberCompany2(String numberCompany2) {
        NumberCompany2 = numberCompany2;
    }

    public EnderecoModel getEnderecoModel() {
        return enderecoModel;
    }

    public void setEnderecoModel(EnderecoModel enderecoModel) {
        this.enderecoModel = enderecoModel;
    }

    public String getEmailCompany() {
        return emailCompany;
    }

    public void setEmailCompany(String emailCompany) {
        this.emailCompany = emailCompany;
    }

    public Boolean getDefaulter() {
        return defaulter;
    }

    public void setDefaulter(Boolean defaulter) {
        this.defaulter = defaulter;
    }

    public List<OrderModel> getSells() {
        return sells;
    }

    public void setSells(List<OrderModel> sells) {
        this.sells = sells;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public Double getValueGenerated() {
        return valueGenerated;
    }

    public void setValueGenerated(Double valorGerado) {
        this.valueGenerated = valorGerado;
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
        CompanyModel that = (CompanyModel) o;
        return Objects.equals(id, that.id) && Objects.equals(nomeFantasia, that.nomeFantasia) && Objects.equals(razaoSocial, that.razaoSocial) && Objects.equals(numberCompany1, that.numberCompany1) && Objects.equals(NumberCompany2, that.NumberCompany2) && Objects.equals(enderecoModel, that.enderecoModel) && Objects.equals(emailCompany, that.emailCompany) && Objects.equals(defaulter, that.defaulter) && Objects.equals(sells, that.sells) && Objects.equals(products, that.products) && Objects.equals(valueGenerated, that.valueGenerated) && Objects.equals(inactive, that.inactive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeFantasia, razaoSocial, numberCompany1, NumberCompany2, enderecoModel, emailCompany, defaulter, sells, products, valueGenerated, inactive);
    }
}
