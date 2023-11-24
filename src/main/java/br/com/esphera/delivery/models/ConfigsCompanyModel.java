package br.com.esphera.delivery.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "configurations")
public class ConfigsCompanyModel {

    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    @JoinColumn(name = "company_id")
    private CompanyModel companyModel;
    private Double minimumOrderValue;
    private LocalTime hourOpenEstablishment;
    private LocalTime hourCloseEstablishment;
    private Double maximumDistanceDelivery;
    private Double valueKmDelivery;
    private Integer maximumDiscountPercent;

    public ConfigsCompanyModel() {
    }

    public ConfigsCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
        this.minimumOrderValue = 20.0;
        this.hourOpenEstablishment = LocalTime.of(17, 0);
        this.hourCloseEstablishment = LocalTime.of(23, 0);
        this.maximumDistanceDelivery = 20.0;
        this.valueKmDelivery = 1.0;
        this.maximumDiscountPercent = 20;
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

    public Double getMinimumOrderValue() {
        return minimumOrderValue;
    }

    public void setMinimumOrderValue(Double minimumOrderValue) {
        this.minimumOrderValue = minimumOrderValue;
    }

    public LocalTime getHourOpenEstablishment() {
        return hourOpenEstablishment;
    }

    public void setHourOpenEstablishment(LocalTime hourOpenEstablishment) {
        this.hourOpenEstablishment = hourOpenEstablishment;
    }

    public LocalTime getHourCloseEstablishment() {
        return hourCloseEstablishment;
    }

    public void setHourCloseEstablishment(LocalTime hourCloseEstablishment) {
        this.hourCloseEstablishment = hourCloseEstablishment;
    }

    public Double getMaximumDistanceDelivery() {
        return maximumDistanceDelivery;
    }

    public void setMaximumDistanceDelivery(Double maximumDistanceDelivery) {
        this.maximumDistanceDelivery = maximumDistanceDelivery;
    }

    public Double getValueKmDelivery() {
        return valueKmDelivery;
    }

    public void setValueKmDelivery(Double valueKmDelivery) {
        this.valueKmDelivery = valueKmDelivery;
    }

    public Integer getMaximumDiscountPercent() {
        return maximumDiscountPercent;
    }

    public void setMaximumDiscountPercent(Integer maximumDiscountPercent) {
        this.maximumDiscountPercent = maximumDiscountPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigsCompanyModel that = (ConfigsCompanyModel) o;
        return Objects.equals(id, that.id) && Objects.equals(companyModel, that.companyModel) && Objects.equals(minimumOrderValue, that.minimumOrderValue) && Objects.equals(hourOpenEstablishment, that.hourOpenEstablishment) && Objects.equals(hourCloseEstablishment, that.hourCloseEstablishment) && Objects.equals(maximumDistanceDelivery, that.maximumDistanceDelivery) && Objects.equals(valueKmDelivery, that.valueKmDelivery) && Objects.equals(maximumDiscountPercent, that.maximumDiscountPercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyModel, minimumOrderValue, hourOpenEstablishment, hourCloseEstablishment, maximumDistanceDelivery, valueKmDelivery, maximumDiscountPercent);
    }
}
