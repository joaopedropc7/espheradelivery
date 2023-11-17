package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {
}
