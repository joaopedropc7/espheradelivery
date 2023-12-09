package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.MotoboysModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MotoboyRepository extends JpaRepository<MotoboysModel, Integer> {

    List<MotoboysModel> findAllByCompanyModel(CompanyModel companyModel);

    @Query("SELECT COALESCE(MAX(p.idLocalMotoboy), 0) FROM MotoboysModel p WHERE p.companyModel.id = :companyId")
    Integer findMaxIdLocalByCompany(@Param("companyId") Integer companyId);

    MotoboysModel findMotoboysModelByIdLocalMotoboyAndCompanyModel(Integer idLocalMotoboy, CompanyModel companyModel);

}
