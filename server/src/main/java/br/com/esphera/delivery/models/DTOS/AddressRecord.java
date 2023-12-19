package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotNull;

public record AddressRecord(
        @NotNull(message = "O campo logradouro é obrigatório")
        String logradouro,
        @NotNull(message = "O campo CEP é obrigatório")
        String cep,
        @NotNull(message = "O campo complemento é obrigatório")
        String complemento,
        @NotNull(message = "O campo bairro é obrigatório")
        String bairro,
        @NotNull(message = "O campo localidade é obrigatório")
        String localidade,
        @NotNull(message = "O campo UF é obrigatório")
        String UF,
        @NotNull(message = "O campo número é obrigatório")
        String numberHouse){
}
