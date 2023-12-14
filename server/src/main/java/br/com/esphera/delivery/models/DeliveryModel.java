package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.AddressRecord;
import br.com.esphera.delivery.models.DTOS.DeliveryRecord;
import br.com.esphera.delivery.models.DTOS.DistanceDurationDTO;
import br.com.esphera.delivery.models.Enums.StatusDelivery;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "delivery")
public class DeliveryModel extends RepresentationModel<DeliveryModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderModel orderModel;
    @OneToOne
    @JoinColumn(name = "motoboy_id")
    private MotoboysModel motoboysModel;
    private LocalDateTime dateDeliveryStart;
    private LocalDateTime dateDeliveryFinished;
    private Double value;
    private Double distance;
    private Boolean cancelled;
    private String logradouro;
    private String cep;
    private String complemento;
    private String bairro;
    private String localidade;
    private String UF;
    private String numberHouse;

    public DeliveryModel() {
    }

    public DeliveryModel(DeliveryRecord deliveryRecord, DistanceDurationDTO distanceDurationDTO, Double valueDelivery, AddressRecord addressRecord) {
        this.orderModel = deliveryRecord.orderModel();
        this.motoboysModel = null;
        this.dateDeliveryStart = null;
        this.dateDeliveryFinished = null;
        this.value = valueDelivery;
        this.distance = distanceDurationDTO.distance();
        this.cancelled = false;
        this.logradouro = addressRecord.logradouro();
        this.cep = addressRecord.cep();
        this.complemento = addressRecord.complemento();
        this.bairro = addressRecord.bairro();
        this.localidade = addressRecord.localidade();
        this.UF = addressRecord.UF();
        this.numberHouse = addressRecord.numberHouse();
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

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
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
