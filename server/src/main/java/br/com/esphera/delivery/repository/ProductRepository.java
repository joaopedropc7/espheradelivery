package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CategoryModel;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    Page<ProductModel> findProductModelsByCompanyModelAndCategoryModel(CompanyModel companyModel, CategoryModel categoryModel, Pageable pageable);

    Page<ProductModel> findProductModelByCompanyModel(CompanyModel companyModel, Pageable pageable);

    @Query("SELECT p FROM ProductModel p WHERE LOWER(TRIM(p.name)) LIKE LOWER(TRIM(CONCAT('%', :name, '%'))) AND p.companyModel = :companyModel")
    Page<ProductModel> findProductModelByName(@Param("name") String name, Pageable pageable, CompanyModel companyModel);

    @Query("SELECT p FROM ProductModel p WHERE p.companyModel = :companyModel ORDER BY p.sales DESC")
    Page<ProductModel> findProductsByCompanyOrderBySalesDesc(CompanyModel companyModel, Pageable pageable);

    @Query("SELECT p FROM ProductModel p WHERE p.companyModel = :companyModel ORDER BY p.sales DESC")
    Optional<ProductModel> findTopProductWithMostSalesByCompany(CompanyModel companyModel);

}
