package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.CreateCouponDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "discount_coupon", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"company_id", "name"})
})
public class CouponModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private CompanyModel companyModel;
    private String name;
    private Double percentDiscount;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime expireDate;
    private Integer numberUses;
    private Boolean limitUses;
    private Integer numberUsesAllowed;
    private Boolean active;

    public CouponModel(CreateCouponDTO dto, CompanyModel companyModel){
        this.companyModel = companyModel;
        this.name = dto.name();
        this.percentDiscount = dto.percentDiscount();
        this.createdAt = LocalDateTime.now();
        this.startDate = LocalDateTime.parse(dto.startDate());
        this.expireDate = LocalDateTime.parse(dto.expireDate());
        this.numberUses = 0;
        this.limitUses = dto.limitUses();
        this.numberUsesAllowed = dto.numberUsesAllowed();
        this.active = true;
    }

    public CouponModel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CompanyModel getCompanyModel() {
        return companyModel;
    }

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(Double percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getNumberUses() {
        return numberUses;
    }

    public void setNumberUses(Integer numberUses) {
        this.numberUses = numberUses;
    }

    public Boolean getLimitUses() {
        return limitUses;
    }

    public void setLimitUses(Boolean limitUses) {
        this.limitUses = limitUses;
    }

    public Integer getNumberUsesAllowed() {
        return numberUsesAllowed;
    }

    public void setNumberUsesAllowed(Integer numberUsesAllowed) {
        this.numberUsesAllowed = numberUsesAllowed;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}

