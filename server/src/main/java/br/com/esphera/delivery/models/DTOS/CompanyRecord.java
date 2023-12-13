package br.com.esphera.delivery.models.DTOS;

public record CompanyRecord(
        String nomeFantasia,
        String razaoSocial,
        String cpf,
        String cnpj,
        String nameContact,
        String numberCompany1,
        String numberCompany2,
        String emailCompany,
        AddressRecord addressRecord
        ) {
}
