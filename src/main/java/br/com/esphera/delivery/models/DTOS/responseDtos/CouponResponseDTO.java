package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.CouponModel;

public record CouponResponseDTO(
        Integer id,
        String name,
        Double percentDiscount,
        String createdAt,
        String startDate,
        String expireDate,
        Integer numberUses,
        Boolean limitUses,
        Integer numberUsesAllowed,
        Boolean active,
        Integer companyId
) {
    public CouponResponseDTO(CouponModel couponModel){
        this(
                couponModel.getId(),
                couponModel.getName(),
                couponModel.getPercentDiscount(),
                couponModel.getCreatedAt().toString(),
                couponModel.getStartDate().toString(),
                couponModel.getExpireDate().toString(),
                couponModel.getNumberUses(),
                couponModel.getLimitUses(),
                couponModel.getNumberUsesAllowed(),
                couponModel.getActive(),
                couponModel.getCompanyModel().getId()
        );
    }

}
