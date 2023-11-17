package br.com.esphera.delivery.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "company")
public class CompanyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeFantasia;
    private String razaoSocial;
    private String numberCompany1;
    private String getNumberCompany2;
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private EnderecoModel enderecoModel;
    private String emailCompany;
    private Boolean defaulter;
    @OneToMany
    @JoinColumn(name = "sells")
    private List<SellModel> sells;
    @OneToMany
    @JoinColumn(name = "products")
    private List<ProductModel> products;




}
