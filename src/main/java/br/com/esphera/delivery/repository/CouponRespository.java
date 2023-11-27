package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.CouponModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRespository extends JpaRepository<CouponModel, Integer> {

    CouponModel findCouponModelByNameAndCompanyModel(String name, CompanyModel companyModel);

    List<CouponModel> findCouponModelByCompanyModel(CompanyModel companyModel);

}
