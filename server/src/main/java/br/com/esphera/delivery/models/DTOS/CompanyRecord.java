package br.com.esphera.delivery.models.DTOS;

import jakarta.validation.constraints.NotNull;

public record CompanyRecord(
        @NotNull(message = "O campo nome fantasia é obrigatório")
        String nomeFantasia,
        @NotNull(message = "O campo razão social é obrigatório")
        String razaoSocial,
        String cpf,
        String cnpj,
        @NotNull(message = "O campo nome do contato é obrigatório")
        String nameContact,
        @NotNull(message = "O campo número do contato é obrigatório")
        String numberCompany1,
        String numberCompany2,
        @NotNull(message = "O campo email é obrigatório")
        String emailCompany,
        @NotNull(message = "O campo endereço é obrigatório")
        AddressRecord addressRecord
        ) {
}
