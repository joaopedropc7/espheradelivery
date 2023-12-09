package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.ProductEntryModel;
import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductEntryRepository extends JpaRepository<ProductEntryModel, Integer> {

    List<ProductEntryModel> findProductEntryModelsByCompanyModel(CompanyModel companyModel);

    @Query("SELECT COALESCE(MAX(p.idLocalEntry), 0) FROM ProductEntryModel p WHERE p.companyModel.id = :companyId")
    Integer findMaxIdLocalByCompany(@Param("companyId") Integer companyId);

    ProductEntryModel findProductEntryModelByIdLocalEntryAndCompanyModel(Integer idLocalEntry, CompanyModel companyModel);

}
