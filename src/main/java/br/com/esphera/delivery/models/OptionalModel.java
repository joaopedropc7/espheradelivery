package br.com.esphera.delivery.models;

import jakarta.persistence.*;

@Entity
@Table(name = "optional_products")
public class OptionalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String image;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    private ProductModel productModel;

}
