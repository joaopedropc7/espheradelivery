package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Integer> {

    List<OrderModel> findByStatusOrder(StatusOrder statusOrder);


}
