package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.SellModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellRepository extends JpaRepository<SellModel, Integer> {
}
