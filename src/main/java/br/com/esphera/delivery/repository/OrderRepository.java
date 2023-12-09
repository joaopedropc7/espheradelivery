package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.OrderModel;
import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Integer> {

    List<OrderModel> findByStatusOrderAndCompanyModel(StatusOrder statusOrder, CompanyModel companyModel);

    List<OrderModel> findOrderModelsByCompanyModel(CompanyModel companyModel);


    @Query("SELECT COALESCE(MAX(p.idLocalOrder), 0) FROM OrderModel p WHERE p.companyModel.id = :companyId")
    Integer findMaxIdLocalByCompany(@Param("companyId") Integer companyId);

    OrderModel findOrderModelByIdLocalOrderAndCompanyModel(Integer idLocalOrder, CompanyModel companyModel);

}
