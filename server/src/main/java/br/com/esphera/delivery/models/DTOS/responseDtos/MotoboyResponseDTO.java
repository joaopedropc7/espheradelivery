package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.MotoboysModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;

import java.util.List;

public record MotoboyResponseDTO(
        Integer idMotoboy,
        String nameMotoboy,
        String email,
        String number,
        Boolean inactive,
        Integer idCompany,
        Links links
) {
    public MotoboyResponseDTO(MotoboysModel motoboysModel) {
        this(
                motoboysModel.getId(),
                motoboysModel.getNameMotoboy(),
                motoboysModel.getEmail(),
                motoboysModel.getNumber(),
                motoboysModel.getInactive(),
                motoboysModel.getCompanyModel().getId(),
                motoboysModel.getLinks()
        );
    }

    public static Page<MotoboyResponseDTO> convert(Page<MotoboysModel> motoboysModel) {
        List<MotoboyResponseDTO> motoboyDTOs = motoboysModel
                .stream()
                .map(MotoboyResponseDTO::new)
                .toList();
        return new PageImpl<>(motoboyDTOs, motoboysModel.getPageable(), motoboysModel.getTotalElements());
    }
}
