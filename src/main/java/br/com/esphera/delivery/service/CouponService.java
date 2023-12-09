package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.CouponModel;
import br.com.esphera.delivery.models.DTOS.CreateCouponDTO;
import br.com.esphera.delivery.repository.CouponRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRespository couponRespository;

    @Autowired
    private CompanyService companyService;

    public CouponModel createdCoupon(CreateCouponDTO dto, Integer companyId){
        Integer lastInsert = couponRespository.findMaxIdLocalByCompany(companyId);
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        if (couponRespository.findCouponModelByNameAndCompanyModel(dto.name(), companyModel) != null) throw new ResourceNotFoundException("Já existe um cupom come este nome.");
        CouponModel couponModel = new CouponModel(dto, companyModel, lastInsert);
        return couponRespository.save(couponModel);
    }

    public CouponModel getCouponById(Integer couponId){
        CouponModel couponModel = couponRespository.findById(couponId).orElseThrow(() -> new ResourceNotFoundException("Nenhum cupom encontrado com este Id!"));
        return couponModel;
    }

    public Double getDiscountByCoupon(String nameCoupon, Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        CouponModel couponModel = couponRespository.findCouponModelByNameAndCompanyModel(nameCoupon, companyModel);
        if(couponModel == null) throw new ResourceNotFoundException("Nenhum cupom encontrado!");
        if(!couponModel.getActive()) throw new ResourceNotFoundException("Este cupom não está ativo!");
        if (couponModel.getLimitUses() && couponModel.getNumberUses() >= couponModel.getNumberUsesAllowed()) throw new ResourceNotFoundException("Este cupom já atingiu o limite de usos!");
        if(LocalDateTime.now().isBefore(couponModel.getStartDate()) || LocalDateTime.now().isAfter(couponModel.getExpireDate())) throw new ResourceNotFoundException("Cupom expirado ou não entrou no prazo de vigência,");
        return couponModel.getPercentDiscount();
    }

    public void inactiveCoupon (Integer couponId, Integer companyId){
        CouponModel couponModel = getCouponById(couponId);
        verifyCouponBelongsToCompany(couponModel, companyId);
        couponModel.setActive(Boolean.FALSE);
        couponRespository.save(couponModel);
    }

    public List<CouponModel> findCouponsByCompany(Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        return couponRespository.findCouponModelByCompanyModel(companyModel);
    }

    public CouponModel getCouponByLocalIdAndCompany(Integer idLocalCoupon, Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        return couponRespository.findCouponModelByIdLocalCouponAndCompanyModel(idLocalCoupon, companyModel);
    }

    public void verifyCouponBelongsToCompany(CouponModel couponModel, Integer companyId){
        if(!couponModel.getCompanyModel().getId().equals(companyId)) throw new ResourceNotFoundException("Este cupom não pertence a esta empresa!");
    }
}
