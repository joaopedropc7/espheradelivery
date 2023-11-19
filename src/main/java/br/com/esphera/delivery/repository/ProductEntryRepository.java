package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.ProductEntryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductEntryRepository extends JpaRepository<ProductEntryModel, Integer> {
}
