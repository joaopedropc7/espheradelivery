package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.UserRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.Objects;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String cpf;
    private String email;
    private String numberCellphone;
    private Boolean inactive;

    public UserModel() {
    }

    public UserModel(UserRecord dto) {
        this.name = dto.name();
        this.cpf = dto.cpf();
        this.email = dto.email();
        this.numberCellphone = dto.numberCellphone();
        this.inactive = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberCellphone() {
        return numberCellphone;
    }

    public void setNumberCellphone(String numberCellphone) {
        this.numberCellphone = numberCellphone;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(id, userModel.id) && Objects.equals(name, userModel.name) && Objects.equals(cpf, userModel.cpf) && Objects.equals(email, userModel.email) && Objects.equals(numberCellphone, userModel.numberCellphone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cpf, email, numberCellphone);
    }
}
