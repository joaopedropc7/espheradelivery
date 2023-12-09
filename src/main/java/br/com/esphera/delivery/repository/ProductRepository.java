package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CategoryModel;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    List<ProductModel> findProductModelsByCompanyModelAndCategoryModel(CompanyModel companyModel, CategoryModel categoryModel);

    @Query("select p from ProductModel p WHERE p.companyModel = :companyModel")
    List<ProductModel> finddProductByComapanyId(CompanyModel companyModel);

    @Query("SELECT COALESCE(MAX(p.idLocalByCompany), 0) FROM ProductModel p WHERE p.companyModel.id = :companyId")
    Integer findMaxIdLocalByCompany(@Param("companyId") Integer companyId);

    ProductModel findProductModelByIdLocalByCompanyAndCompanyModel(Integer idLocalByCompany, CompanyModel companyModel);
}
