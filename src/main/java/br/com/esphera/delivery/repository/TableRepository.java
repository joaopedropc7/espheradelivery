package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.TableModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<TableModel, Integer> {

    Optional<TableModel> findTableModelByCompanyModelAndTableNumber(CompanyModel companyModel, Integer tableNumber);

    List<TableModel> findAllByCompanyModel(CompanyModel companyModel);


    Integer countTableModelByCompanyModel(CompanyModel companyModel);
}
