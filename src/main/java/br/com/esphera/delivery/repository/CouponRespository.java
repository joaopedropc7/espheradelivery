package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.CouponModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRespository extends JpaRepository<CouponModel, Integer> {

    CouponModel findCouponModelByNameAndCompanyModel(String name, CompanyModel companyModel);

    List<CouponModel> findCouponModelByCompanyModel(CompanyModel companyModel);

    @Query("SELECT COALESCE(MAX(p.idLocalCoupon), 0) FROM CouponModel p WHERE p.companyModel.id = :companyId")
    Integer findMaxIdLocalByCompany(@Param("companyId") Integer companyId);

    CouponModel findCouponModelByIdLocalCouponAndCompanyModel(Integer idLocalCoupon, CompanyModel companyModel);

}
