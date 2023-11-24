package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.Enums.StatusDelivery;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "delivery")
public class DeliveryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderModel orderModel;
    @OneToOne
    @JoinColumn(name = "motoboy_id")
    private MotoboysModel motoboysModel;
    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressModel addressModel;
    private StatusDelivery statusDelivery;
    private LocalDateTime dateDeliveryStart;
    private LocalDateTime dateDeliveryFinished;
    private Double value;
    private Double distance;

    public DeliveryModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public MotoboysModel getMotoboysModel() {
        return motoboysModel;
    }

    public void setMotoboysModel(MotoboysModel motoboysModel) {
        this.motoboysModel = motoboysModel;
    }

    public AddressModel getAddressModel() {
        return addressModel;
    }

    public StatusDelivery getStatusDelivery() {
        return statusDelivery;
    }

    public void setStatusDelivery(StatusDelivery statusDelivery) {
        this.statusDelivery = statusDelivery;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public LocalDateTime getDateDeliveryStart() {
        return dateDeliveryStart;
    }

    public void setDateDeliveryStart(LocalDateTime dateDeliveryStart) {
        this.dateDeliveryStart = dateDeliveryStart;
    }

    public LocalDateTime getDateDeliveryFinished() {
        return dateDeliveryFinished;
    }

    public void setDateDeliveryFinished(LocalDateTime dateDeliveryFinished) {
        this.dateDeliveryFinished = dateDeliveryFinished;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryModel that = (DeliveryModel) o;
        return Objects.equals(id, that.id) && Objects.equals(orderModel, that.orderModel) && Objects.equals(motoboysModel, that.motoboysModel) && Objects.equals(addressModel, that.addressModel) && Objects.equals(dateDeliveryStart, that.dateDeliveryStart) && Objects.equals(dateDeliveryFinished, that.dateDeliveryFinished) && Objects.equals(value, that.value) && Objects.equals(distance, that.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderModel, motoboysModel, addressModel, dateDeliveryStart, dateDeliveryFinished, value, distance);
    }
}
