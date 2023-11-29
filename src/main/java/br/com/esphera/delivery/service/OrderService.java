package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.*;
import br.com.esphera.delivery.models.DTOS.DeliveryRecord;
import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.Enums.TypeDelivery;
import br.com.esphera.delivery.repository.OrderRepository;
import br.com.esphera.delivery.repository.ShoppingCartRepository;
import br.com.esphera.delivery.validations.sells.ValidationSales;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private List<ValidationSales> validationSales;

    @Autowired
    private ProductService productService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private CouponService couponService;
    

    public OrderModel createSell(OrderCreateRecord data, Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        ShoppingCartModel shoppingCartModel = shoppingCartRepository.findById(data.shoppingCartId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        validationSales.forEach(validationSales -> validationSales.valid(data, shoppingCartModel));
        Double discount = 0.0;
        if(data.couponName() != null){
            discount = couponService.getDiscountByCoupon(data.couponName(), companyId);
            if(discount == null)throw new ResourceNotFoundException("Cupom inválido");
        }
        OrderModel orderModel = new OrderModel(data, shoppingCartModel, companyModel, discount);
        orderRepository.save(orderModel);
        if(data.typeDelivery() == TypeDelivery.DELIVERY){
            DeliveryRecord deliveryRecord = new DeliveryRecord(orderModel, data.addressRecord());
            DeliveryModel deliveryModel = deliveryService.createDelivery(companyModel,deliveryRecord);
            orderModel.setDeliveryModel(deliveryModel);
            orderModel.ajustValueDelivery(deliveryModel.getValue());

        }else{
            orderModel.setDeliveryModel(null);
        }

        orderRepository.save(orderModel);
        shoppingCartModel.getProductCartItems().forEach(productCartItemModel -> {
            productService.sellProduct(productCartItemModel.getProduct(), productCartItemModel.getQuantity());
        });
        companyService.incrementValueGenerated(companyModel,orderModel.getSellValueWithDiscount());
        return orderModel;
    }

    public List<OrderModel> findAllSells(Integer idCompany){
        CompanyModel companyModel = companyService.getCompanyById(idCompany);
        List<OrderModel> sells = orderRepository.findOrderModelsByCompanyModel(companyModel);
        return sells;
    }

    public OrderModel findByIdSell(Integer id){
        OrderModel orderModel = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        return orderModel;
    }

    public void cancelSell(Integer id){
        OrderModel orderModel = findByIdSell(id);
        if(orderModel.getTypeDelivery() == TypeDelivery.DELIVERY){
            deliveryService.cancelDelivery(orderModel.getDeliveryModel());
        }
        orderModel.getShoppingCartModel().getProductCartItems().forEach(product -> {
            productService.revertSellProduct(product.getProduct(), product.getQuantity());
        });
        orderModel.setOrderCancelled(true);
        orderModel.setStatusOrder(StatusOrder.Cancelado);
        orderRepository.save(orderModel);
        companyService.reverseValueGenerated(orderModel.getCompanyModel() ,orderModel.getSellValueWithDiscount());
    }

    public List<OrderModel> findByStatusOrder(StatusOrder statusOrder, Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        List<OrderModel> orderModels = orderRepository.findByStatusOrderAndCompanyModel(statusOrder, companyModel);
        return orderModels;
    }

    //Pedido em preparo
    public void setOrderPrepared(Integer id){
        OrderModel orderModel = findByIdSell(id);
        orderModel.setStatusOrder(StatusOrder.EmPreparo);
        orderRepository.save(orderModel);
    }

    //Set order Delivery
    public void setOrderDelivery(Integer idOrder, Integer motoboyId){
        OrderModel orderModel = findByIdSell(idOrder);
        if(orderModel.getTypeDelivery() == TypeDelivery.RETIRADA){
            throw new ResourceNotFoundException("Este pedido é para retirada, não é possível colocar o mesmo para entrega.");
        }
        deliveryService.setDeliveryInRoute(orderModel.getDeliveryModel(), motoboyId);
        orderModel.setStatusOrder(StatusOrder.Rota);
        orderRepository.save(orderModel);
    }

    public void setOrderReadyForCollect(Integer idOrder){
        OrderModel orderModel = findByIdSell(idOrder);
        if(orderModel.getTypeDelivery() == TypeDelivery.DELIVERY){
            throw new ResourceNotFoundException("Este pedido é para entrega, não é posível colocar para retirada!");
        }
        orderModel.setStatusOrder(StatusOrder.ProntoRetirada);
        orderRepository.save(orderModel);
    }

    public void setOrderFinished(Integer id){
        OrderModel orderModel = findByIdSell(id);
        if(orderModel.getTypeDelivery() == TypeDelivery.DELIVERY){
            deliveryService.setDeliveryFinished(orderModel.getDeliveryModel());
        }
        orderModel.setDateFinishOrder(LocalDateTime.now());
        orderModel.setStatusOrder(StatusOrder.Finalizado);
        orderRepository.save(orderModel);
    }



}
