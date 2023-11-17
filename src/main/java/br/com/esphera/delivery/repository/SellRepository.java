package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.SellModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellRepository extends JpaRepository<SellModel, Integer> {

    List<SellModel> findByStatusOrder(StatusOrder statusOrder);


}
