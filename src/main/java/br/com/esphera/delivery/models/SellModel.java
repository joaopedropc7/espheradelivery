package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.SellCreateRecord;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "sells")
public class SellModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String clientName;
    private String cpf;
    private String numberCellphone;
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private EnderecoModel enderecoModel;
    @OneToOne
    @JoinColumn(name = "shoppingcart_id")
    private ShoppingCartModel shoppingCartModel;
    private Double sellValue;
    private Double discount;
    private Double sellValueWithDiscount;
    private Double deliveryValue;
    private LocalDateTime orderDate;
    private StatusOrder statusOrder;
    private Boolean orderCancelled;

    public SellModel() {
    }

    public SellModel(SellCreateRecord data, EnderecoModel enderecoModel, ShoppingCartModel shoppingCartModel) {
        this.clientName = data.clientName();
        this.cpf = data.cpf();
        this.numberCellphone = data.numberCellphone();
        this.enderecoModel = enderecoModel;
        this.shoppingCartModel = shoppingCartModel;
        this.sellValue = shoppingCartModel.getTotalValue();
        this.statusOrder = StatusOrder.Recebido;
        this.discount = data.discount();
        this.sellValueWithDiscount = shoppingCartModel.getTotalValue() - data.discount();
        this.orderDate = LocalDateTime.now();
        this.orderCancelled = false;
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

    public EnderecoModel getEnderecoModel() {
        return enderecoModel;
    }

    public void setEnderecoModel(EnderecoModel enderecoModel) {
        this.enderecoModel = enderecoModel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellModel sellModel = (SellModel) o;
        return Objects.equals(id, sellModel.id) && Objects.equals(clientName, sellModel.clientName) && Objects.equals(cpf, sellModel.cpf) && Objects.equals(numberCellphone, sellModel.numberCellphone) && Objects.equals(enderecoModel, sellModel.enderecoModel) && Objects.equals(shoppingCartModel, sellModel.shoppingCartModel) && Objects.equals(sellValue, sellModel.sellValue) && Objects.equals(discount, sellModel.discount) && Objects.equals(sellValueWithDiscount, sellModel.sellValueWithDiscount) && Objects.equals(deliveryValue, sellModel.deliveryValue) && Objects.equals(orderDate, sellModel.orderDate) && statusOrder == sellModel.statusOrder && Objects.equals(orderCancelled, sellModel.orderCancelled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientName, cpf, numberCellphone, enderecoModel, shoppingCartModel, sellValue, discount, sellValueWithDiscount, deliveryValue, orderDate, statusOrder, orderCancelled);
    }
}
