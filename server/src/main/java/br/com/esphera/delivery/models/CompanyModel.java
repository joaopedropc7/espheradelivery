package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.AddressRecord;
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
    @OneToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "banner_image_id")
    private FileEntity bannerImage;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logo_image_id")
    private FileEntity logoImage;
    private String logradouro;
    private String cep;
    private String complemento;
    private String bairro;
    private String localidade;
    private String UF;
    private String numberHouse;
    private String idLocalCompanyMaps;

    public CompanyModel() {
    }

    public CompanyModel(CompanyRecord dto, AddressRecord addressRecord, String placeId) {
        this.nomeFantasia = dto.nomeFantasia();
        this.razaoSocial = dto.razaoSocial();
        this.cnpj = dto.cnpj();
        this.cpf = dto.cpf();
        this.nameContact = dto.nameContact();
        this.numberCompany1 = dto.numberCompany1();
        this.NumberCompany2 = dto.numberCompany2();
        this.emailCompany = dto.emailCompany();
        this.defaulter = false;
        this.valueGenerated = 0.0;
        this.inactive = false;
        this.dateRegister = LocalDate.now();
        this.dateLastPayment = null;
        this.dateNextPayment = null;
        this.signatureActive = false;
        this.logradouro = addressRecord.logradouro();
        this.cep = addressRecord.cep();
        this.complemento = addressRecord.complemento();
        this.bairro = addressRecord.bairro();
        this.localidade = addressRecord.localidade();
        this.UF = addressRecord.UF();
        this.numberHouse = addressRecord.numberHouse();
        this.idLocalCompanyMaps = placeId;
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getNumberHouse() {
        return numberHouse;
    }

    public void setNumberHouse(String numberHouse) {
        this.numberHouse = numberHouse;
    }
}
