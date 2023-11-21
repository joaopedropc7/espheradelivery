package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.Enums.TypeDelivery;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "sells")
public class OrderModel {

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
    private TypeDelivery typeDelivery;

    public OrderModel() {
    }

    public OrderModel(OrderCreateRecord data, EnderecoModel enderecoModel, ShoppingCartModel shoppingCartModel) {
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
        this.typeDelivery = data.typeDelivery();
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

    public TypeDelivery getTypeDelivery() {
        return typeDelivery;
    }

    public void setTypeDelivery(TypeDelivery typeDelivery) {
        this.typeDelivery = typeDelivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderModel orderModel = (OrderModel) o;
        return Objects.equals(id, orderModel.id) && Objects.equals(clientName, orderModel.clientName) && Objects.equals(cpf, orderModel.cpf) && Objects.equals(numberCellphone, orderModel.numberCellphone) && Objects.equals(enderecoModel, orderModel.enderecoModel) && Objects.equals(shoppingCartModel, orderModel.shoppingCartModel) && Objects.equals(sellValue, orderModel.sellValue) && Objects.equals(discount, orderModel.discount) && Objects.equals(sellValueWithDiscount, orderModel.sellValueWithDiscount) && Objects.equals(deliveryValue, orderModel.deliveryValue) && Objects.equals(orderDate, orderModel.orderDate) && statusOrder == orderModel.statusOrder && Objects.equals(orderCancelled, orderModel.orderCancelled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientName, cpf, numberCellphone, enderecoModel, shoppingCartModel, sellValue, discount, sellValueWithDiscount, deliveryValue, orderDate, statusOrder, orderCancelled);
    }
}
