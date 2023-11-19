package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.MotoboysModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoboyRepository extends JpaRepository<MotoboysModel, Integer> {
}
