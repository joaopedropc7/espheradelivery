package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.controller.FIleController;
import br.com.esphera.delivery.models.CompanyModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public record CompanyInfoResponseDTO(
        Integer companyId,
        String companyName,
        Double valueGenerated) {
    public CompanyInfoResponseDTO(CompanyModel companyModel) {
        this(
                companyModel.getId(),
                companyModel.getNomeFantasia(),
                companyModel.getValueGenerated());
    }
}
