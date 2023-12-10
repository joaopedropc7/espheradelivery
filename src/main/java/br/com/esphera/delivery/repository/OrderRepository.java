package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.OrderModel;
import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Integer> {

    Page<OrderModel> findByStatusOrderAndCompanyModel(StatusOrder statusOrder, CompanyModel companyModel, Pageable pageable);

    Page<OrderModel> findOrderModelsByCompanyModel(CompanyModel companyModel, Pageable pageable);



}
