package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.AddressRecord;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "endereco")
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String logradouro;
    private String cep;
    private String complemento;
    private String bairro;
    private String localidade;
    private String UF;
    private String numberHouse;

    public EnderecoModel() {
    }

    public EnderecoModel(AddressRecord data){
        this.logradouro = data.logradouro();
        this.cep = data.cep();
        this.complemento = data.complemento();
        this.bairro = data.bairro();
        this.localidade = data.localidade();
        this.UF = data.UF();
        this.numberHouse = data.numberHouse();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoModel that = (EnderecoModel) o;
        return Objects.equals(id, that.id) && Objects.equals(logradouro, that.logradouro) && Objects.equals(cep, that.cep) && Objects.equals(complemento, that.complemento) && Objects.equals(bairro, that.bairro) && Objects.equals(localidade, that.localidade) && Objects.equals(UF, that.UF) && Objects.equals(numberHouse, that.numberHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, logradouro, cep, complemento, bairro, localidade, UF, numberHouse);
    }
}
