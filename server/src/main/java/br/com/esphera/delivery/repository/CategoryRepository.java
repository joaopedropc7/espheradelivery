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

public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {

    Page<CategoryModel> findCategoryModelByCompanyModel(CompanyModel companyModel, Pageable pageable);

    @Query("SELECT p FROM CategoryModel p WHERE LOWER(TRIM(p.categoryName)) LIKE LOWER(TRIM(CONCAT('%', :name, '%'))) AND p.companyModel = :companyModel")
    Page<CategoryModel> findCategoryModelByCategoryName(@Param("name") String name, Pageable pageable, CompanyModel companyModel);

}
