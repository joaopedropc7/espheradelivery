package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.DeliveryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<DeliveryModel, Integer> {
}
