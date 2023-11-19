package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.ProductEntryItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductEntryItemModelRepository extends JpaRepository<ProductEntryItemModel, Integer> {
}
