package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.MotoboysModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotoboyRepository extends JpaRepository<MotoboysModel, Integer> {

    List<MotoboysModel> findAllByCompanyModel(CompanyModel companyModel);

}
