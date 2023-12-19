package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.OptionalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public record OptionalResponseDTO(
        Integer id,
        String name,
        String description,
        Double price
) {
    public OptionalResponseDTO(OptionalModel optionalModel) {
        this(
                optionalModel.getId(),
                optionalModel.getName(),
                optionalModel.getDescription(),
                optionalModel.getPrice()
        );
    }

    public static Page<OptionalResponseDTO> convert(Page<OptionalModel> optionals) {
        List<OptionalResponseDTO> optionalDTOs = optionals
                .stream()
                .map(OptionalResponseDTO::new)
                .toList();
        return new PageImpl<>(optionalDTOs, optionals.getPageable(), optionals.getTotalElements());
    }

    public static List<OptionalResponseDTO> convertToList(List<OptionalModel> optionals) {
        List<OptionalResponseDTO> optionalDTOs = optionals
                .stream()
                .map(OptionalResponseDTO::new)
                .toList();
        return optionalDTOs;
    }
}
