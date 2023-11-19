package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotNull;

public record AddressRecord(@NotNull String logradouro, @NotNull String cep, @NotNull String complemento, @NotNull String bairro, @NotNull String localidade, @NotNull String UF, @NotNull String numberHouse){
}
