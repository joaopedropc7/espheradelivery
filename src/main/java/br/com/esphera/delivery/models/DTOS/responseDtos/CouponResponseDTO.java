package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.CouponModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

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

    public static Page<CouponResponseDTO> convert(Page<CouponModel> couponModels){
        List<CouponResponseDTO> couponsResponseDTO = couponModels
                .stream()
                .map(CouponResponseDTO::new)
                .toList();
        return new PageImpl<>(couponsResponseDTO, couponModels.getPageable(), couponModels.getTotalElements());
    }

}
