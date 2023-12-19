package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.OptionalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionalRepository extends JpaRepository<OptionalModel, Integer> {

    Page<OptionalModel> findAllByCompanyModel(CompanyModel companyModel, Pageable pageable);

}
