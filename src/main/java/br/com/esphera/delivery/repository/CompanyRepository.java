package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<CompanyModel, Integer> {

    @Query("SELECT c FROM CompanyModel c WHERE LOWER(TRIM(c.nomeFantasia)) LIKE LOWER(TRIM(CONCAT('%', :name, '%')))")
    Page<CompanyModel> findCompanyModelByNomeFantasia(@Param("name") String name, Pageable pageable);

}
