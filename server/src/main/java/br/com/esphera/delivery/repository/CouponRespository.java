package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.CouponModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRespository extends JpaRepository<CouponModel, Integer> {

    CouponModel findCouponModelByNameAndCompanyModel(String name, CompanyModel companyModel);

    Page<CouponModel> findCouponModelByCompanyModel(CompanyModel companyModel, Pageable pageable);


}
