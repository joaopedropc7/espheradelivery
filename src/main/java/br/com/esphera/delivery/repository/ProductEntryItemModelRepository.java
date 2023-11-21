package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.ProductEntryItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductEntryItemModelRepository extends JpaRepository<ProductEntryItemModel, Integer> {


    /*@Query("DELETE p FROM ProductEntryItemModel p WHERE p.entryModel.id = :entryId")
    void deleteProductEntryItemModelByEntryModelId(@Param("entryId") Integer entryId);*/
}
