package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CategoryModel;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {

    @Query("select p from CategoryModel p WHERE p.companyModel = :companyModel")
    List<CategoryModel> findCategoryModelByCompanyModel(CompanyModel companyModel);

    @Query("SELECT COALESCE(MAX(p.idLocalCategory), 0) FROM CategoryModel p WHERE p.companyModel.id = :companyId")
    Integer findMaxIdLocalByCompany(@Param("companyId") Integer companyId);

    CategoryModel findCategoryModelByIdLocalCategoryAndCompanyModel(Integer idLocalCategory, CompanyModel companyModel);
}
