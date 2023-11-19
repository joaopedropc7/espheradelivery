package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    @Query("SELECT p FROM ProductModel p WHERE p.categoryModel.id = :categoryId")
    List<ProductModel> findByCategoryId(@Param("categoryId") Integer categoryId);

}
