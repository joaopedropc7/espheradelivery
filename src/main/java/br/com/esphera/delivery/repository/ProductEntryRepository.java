package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.ProductEntryModel;
import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductEntryRepository extends JpaRepository<ProductEntryModel, Integer> {

    List<ProductEntryModel> findProductEntryModelsByCompanyModel(CompanyModel companyModel);

}
