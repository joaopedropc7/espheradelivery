package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.Enums.TypeDelivery;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "sells")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private CompanyModel companyModel;
    private String clientName;
    private String cpf;
    private String numberCellphone;
    @OneToOne
    @JoinColumn(name = "shoppingcart_id")
    private ShoppingCartModel shoppingCartModel;
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private DeliveryModel deliveryModel;
    private Double sellValue;
    private Double discount;
    private Double sellValueWithDiscount;
    private Double deliveryValue;
    private LocalDateTime orderDate;
    private StatusOrder statusOrder;
    private Boolean orderCancelled;
    private TypeDelivery typeDelivery;

    public OrderModel() {
    }

    public OrderModel(OrderCreateRecord data, ShoppingCartModel shoppingCartModel, CompanyModel companyModel) {
        this.clientName = data.clientName();
        this.companyModel = companyModel;
        this.cpf = data.cpf();
        this.numberCellphone = data.numberCellphone();
        this.shoppingCartModel = shoppingCartModel;
        this.sellValue = shoppingCartModel.getTotalValue();
        this.statusOrder = StatusOrder.Recebido;
        this.discount = data.discount();
        this.sellValueWithDiscount = shoppingCartModel.getTotalValue() - data.discount();
        this.orderDate = LocalDateTime.now();
        this.orderCancelled = false;
        this.typeDelivery = data.typeDelivery();
    }

    public void ajustValueDelivery(Double deliveryValue){
        this.deliveryValue = deliveryValue;
        this.sellValueWithDiscount = (sellValue + deliveryValue) - discount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumberCellphone() {
        return numberCellphone;
    }

    public void setNumberCellphone(String numberCellphone) {
        this.numberCellphone = numberCellphone;
    }

    public ShoppingCartModel getShoppingCartModel() {
        return shoppingCartModel;
    }

    public void setShoppingCartModel(ShoppingCartModel shoppingCartModel) {
        this.shoppingCartModel = shoppingCartModel;
    }

    public Double getSellValue() {
        return sellValue;
    }

    public void setSellValue(Double sellValue) {
        this.sellValue = sellValue;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDeliveryValue() {
        return deliveryValue;
    }

    public void setDeliveryValue(Double deliveryValue) {
        this.deliveryValue = deliveryValue;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean getOrderCancelled() {
        return orderCancelled;
    }

    public void setOrderCancelled(Boolean orderCancelled) {
        this.orderCancelled = orderCancelled;
    }

    public Double getSellValueWithDiscount() {
        return sellValueWithDiscount;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public void setSellValueWithDiscount(Double sellValueWithDiscount) {
        this.sellValueWithDiscount = sellValueWithDiscount;
    }

    public CompanyModel getCompanyModel() {
        return companyModel;
    }

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }

    public TypeDelivery getTypeDelivery() {
        return typeDelivery;
    }

    public void setTypeDelivery(TypeDelivery typeDelivery) {
        this.typeDelivery = typeDelivery;
    }

    public DeliveryModel getDeliveryModel() {
        return deliveryModel;
    }

    public void setDeliveryModel(DeliveryModel deliveryModel) {
        this.deliveryModel = deliveryModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderModel that = (OrderModel) o;
        return Objects.equals(id, that.id) && Objects.equals(companyModel, that.companyModel) && Objects.equals(clientName, that.clientName) && Objects.equals(cpf, that.cpf) && Objects.equals(numberCellphone, that.numberCellphone) && Objects.equals(shoppingCartModel, that.shoppingCartModel) && Objects.equals(sellValue, that.sellValue) && Objects.equals(discount, that.discount) && Objects.equals(sellValueWithDiscount, that.sellValueWithDiscount) && Objects.equals(deliveryValue, that.deliveryValue) && Objects.equals(orderDate, that.orderDate) && statusOrder == that.statusOrder && Objects.equals(orderCancelled, that.orderCancelled) && typeDelivery == that.typeDelivery;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientName, cpf, numberCellphone, shoppingCartModel, sellValue, discount, sellValueWithDiscount, deliveryValue, orderDate, statusOrder, orderCancelled);
    }
}
