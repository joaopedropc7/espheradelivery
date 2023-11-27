package br.com.esphera.delivery.models;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant_table")
public class TableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double commandsValue;
    private Integer discountPercent;
    private Integer quantityTotalItems;

}
