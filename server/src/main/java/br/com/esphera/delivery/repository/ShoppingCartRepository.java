package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.ShoppingCartModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartModel, Integer> {
}
