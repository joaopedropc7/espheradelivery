package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.DeliveryModel;
import br.com.esphera.delivery.models.OrderModel;
import br.com.esphera.delivery.models.ShoppingCartModel;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public record OrderResponseDTO(
        Integer id,
        String clientName,
        String cpf,
        String numberCellphone,
        Double sellValue,
        Double discount,
        Double sellValueWithDescount,
        Double deliveryValue,
        String typeDelivery,
        String statusOrder,
        String orderDate,
        Boolean orderCancelled,
        String dateStartOrder,
        String dateFinishOrder,
        ShoppingCartModel shoppingCartModel,
        Integer companyId,
        DeliveryModel deliveryModel
) {
    public OrderResponseDTO(OrderModel orderModel){
        this(
                orderModel.getId(),
                orderModel.getClientName(),
                orderModel.getCpf(),
                orderModel.getNumberCellphone(),
                orderModel.getSellValue(),
                orderModel.getDiscount(),
                orderModel.getSellValueWithDiscount(),
                orderModel.getDeliveryValue(),
                orderModel.getTypeDelivery().toString(),
                orderModel.getStatusOrder().toString(),
                orderModel.getOrderDate().toString(),
                orderModel.getOrderCancelled(),
                orderModel.getDateStartOrder().toString(),
                orderModel.getDateFinishOrder() != null ? orderModel.getDateFinishOrder().toString() : null,
                orderModel.getShoppingCartModel(),
                orderModel.getCompanyModel().getId(),
                orderModel.getDeliveryModel()
        );
    }

    public static Page<OrderResponseDTO> convert(Page<OrderModel> orderModels){
        List<OrderResponseDTO> orderResponseDTO = orderModels
                .stream()
                .map(OrderResponseDTO::new)
                .toList();
        return new PageImpl<>(orderResponseDTO, orderModels.getPageable(), orderModels.getTotalElements());
    }

}
