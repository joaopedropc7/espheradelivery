package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.ProductEntryModel;
import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductEntryRepository extends JpaRepository<ProductEntryModel, Integer> {

    Page<ProductEntryModel> findProductEntryModelsByCompanyModel(CompanyModel companyModel, Pageable pageable);



}
