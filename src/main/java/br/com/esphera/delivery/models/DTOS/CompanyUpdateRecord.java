package br.com.esphera.delivery.models.DTOS;

public record CompanyUpdateRecord(
        String nomeFantasia,
        String razaoSocial,
        String numberCompany1,
        String numberCompany2,
        String emailCompany
) {
}
