package br.com.esphera.delivery.models;

import jakarta.persistence.*;

@Entity
@Table(name = "motoboys")
public class MotoboysModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nameMotoboy;
    private String number;
    private String email;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyModel companyModel;

    //private


}
