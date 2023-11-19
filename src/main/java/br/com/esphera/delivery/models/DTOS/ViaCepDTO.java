package br.com.esphera.delivery.models.DTOS;

public record ViaCepDTO(String logradouro, String cep, String complemento, String bairro, String localidade, String uf) {
}
