package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.CompanyRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "company")
public class CompanyModel extends RepresentationModel<CompanyModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeFantasia;
    private String razaoSocial;
    private String cpf;
    private String cnpj;
    private String nameContact;
    private String numberCompany1;
    private String NumberCompany2;
    private LocalDate dateRegister;
    private LocalDate dateLastPayment;
    private LocalDate dateNextPayment;
    private LocalDate expirationDateSignature;
    private Boolean signatureActive;
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private AddressModel addressModel;
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
    private String idLocalCompanyMaps;
    @OneToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "banner_image_id")
    private FileEntity bannerImage;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logo_image_id")
    private FileEntity logoImage;

    public CompanyModel() {
    }

    public CompanyModel(CompanyRecord dto, AddressModel addressModel, String placeId) {
        this.nomeFantasia = dto.nomeFantasia();
        this.razaoSocial = dto.razaoSocial();
        this.cnpj = dto.cnpj();
        this.cpf = dto.cpf();
        this.nameContact = dto.nameContact();
        this.numberCompany1 = dto.numberCompany1();
        this.NumberCompany2 = dto.numberCompany2();
        this.emailCompany = dto.emailCompany();
        this.addressModel = addressModel;
        this.defaulter = false;
        this.valueGenerated = 0.0;
        this.idLocalCompanyMaps = placeId;
        this.inactive = false;
        this.dateRegister = LocalDate.now();
        this.dateLastPayment = null;
        this.dateNextPayment = null;
        this.signatureActive = false;
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

    public AddressModel getEnderecoModel() {
        return addressModel;
    }

    public void setEnderecoModel(AddressModel addressModel) {
        this.addressModel = addressModel;
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

    public List<ProductModel> getProducts() {
        return products;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNameContact() {
        return nameContact;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public AddressModel getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public String getIdLocalCompanyMaps() {
        return idLocalCompanyMaps;
    }

    public void setIdLocalCompanyMaps(String idLocalCompanyMaps) {
        this.idLocalCompanyMaps = idLocalCompanyMaps;
    }

    public void setSells(List<OrderModel> sells) {
        this.sells = sells;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public FileEntity getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(FileEntity bannerImage) {
        this.bannerImage = bannerImage;
    }

    public FileEntity getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(FileEntity logoImage) {
        this.logoImage = logoImage;
    }

    public LocalDate getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(LocalDate dateRegister) {
        this.dateRegister = dateRegister;
    }

    public LocalDate getDateLastPayment() {
        return dateLastPayment;
    }

    public void setDateLastPayment(LocalDate dateLastPayment) {
        this.dateLastPayment = dateLastPayment;
    }

    public LocalDate getDateNextPayment() {
        return dateNextPayment;
    }

    public void setDateNextPayment(LocalDate dateNextPayment) {
        this.dateNextPayment = dateNextPayment;
    }

    public LocalDate getExpirationDateSignature() {
        return expirationDateSignature;
    }

    public void setExpirationDateSignature(LocalDate expirationDateSignature) {
        this.expirationDateSignature = expirationDateSignature;
    }

    public Boolean getSignatureActive() {
        return signatureActive;
    }

    public void setSignatureActive(Boolean signatureActive) {
        this.signatureActive = signatureActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompanyModel that = (CompanyModel) o;
        return Objects.equals(id, that.id) && Objects.equals(nomeFantasia, that.nomeFantasia) && Objects.equals(razaoSocial, that.razaoSocial) && Objects.equals(cpf, that.cpf) && Objects.equals(cnpj, that.cnpj) && Objects.equals(nameContact, that.nameContact) && Objects.equals(numberCompany1, that.numberCompany1) && Objects.equals(NumberCompany2, that.NumberCompany2) && Objects.equals(addressModel, that.addressModel) && Objects.equals(emailCompany, that.emailCompany) && Objects.equals(defaulter, that.defaulter) && Objects.equals(sells, that.sells) && Objects.equals(products, that.products) && Objects.equals(valueGenerated, that.valueGenerated) && Objects.equals(inactive, that.inactive) && Objects.equals(idLocalCompanyMaps, that.idLocalCompanyMaps) && Objects.equals(bannerImage, that.bannerImage) && Objects.equals(logoImage, that.logoImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, nomeFantasia, razaoSocial, cpf, cnpj, nameContact, numberCompany1, NumberCompany2, addressModel, emailCompany, defaulter, sells, products, valueGenerated, inactive, idLocalCompanyMaps, bannerImage, logoImage);
    }
}
