package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.DeliveryModel;
import br.com.esphera.delivery.models.OrderModel;
import br.com.esphera.delivery.models.ShoppingCartModel;

import java.util.List;

public record OrderResponseDTO(
        Integer id,
        Integer idLocalOrder,
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
                orderModel.getIdLocalOrder(),
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

    public static List<OrderResponseDTO> convert(List<OrderModel> orderModels){
        return orderModels.stream().map(OrderResponseDTO::new).toList();
    }

}
