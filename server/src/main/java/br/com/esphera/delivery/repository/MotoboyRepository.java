package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.MotoboysModel;
import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MotoboyRepository extends JpaRepository<MotoboysModel, Integer> {

    Page<MotoboysModel> findAllByCompanyModel(CompanyModel companyModel, Pageable pageable);

    @Query("SELECT p FROM MotoboysModel p WHERE LOWER(TRIM(p.nameMotoboy)) LIKE LOWER(TRIM(CONCAT('%', :name, '%'))) AND p.companyModel = :companyModel")
    Page<MotoboysModel> findMotoboysModelByNameMotoboy(@Param("name") String name, Pageable pageable, CompanyModel companyModel);

}
