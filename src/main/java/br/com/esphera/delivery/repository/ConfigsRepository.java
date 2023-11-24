package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.ConfigsCompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigsRepository extends JpaRepository<ConfigsCompanyModel, Integer> {

    ConfigsCompanyModel findConfigsCompanyModelByCompanyModel(CompanyModel companyModel);

}
