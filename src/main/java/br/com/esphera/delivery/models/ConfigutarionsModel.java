package br.com.esphera.delivery.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "configurations")
public class ConfigutarionsModel {

    @Id
    @GeneratedValue
    private Integer id;
    private Double minimumOrder;
    private String cnpj;

}
