package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.MotoboysModel;

import java.util.List;

public record MotoboyResponseDTO(
        Integer idMotoboy,
        Integer idLocalMotoboy,
        String nameMotoboy,
        String email,
        String number,
        Boolean inactive,
        Integer idCompany
) {
    public MotoboyResponseDTO(MotoboysModel motoboysModel) {
        this(
                motoboysModel.getId(),
                motoboysModel.getIdLocalMotoboy(),
                motoboysModel.getNameMotoboy(),
                motoboysModel.getEmail(),
                motoboysModel.getNumber(),
                motoboysModel.getInactive(),
                motoboysModel.getCompanyModel().getId()
        );
    }

    public static List<MotoboyResponseDTO> convert(List<MotoboysModel> motoboysModel) {
        return motoboysModel.stream().map(MotoboyResponseDTO::new).toList();
    }
}
