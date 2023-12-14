package br.com.esphera.delivery.service;

import br.com.esphera.delivery.controller.OrderController;
import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.*;
import br.com.esphera.delivery.models.DTOS.DeliveryRecord;
import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.Enums.TypeDelivery;
import br.com.esphera.delivery.repository.OrderRepository;
import br.com.esphera.delivery.repository.ShoppingCartRepository;
import br.com.esphera.delivery.validations.sells.ValidationSales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @Autowired
    private AddressService addressService;

    public OrderModel createSell(OrderCreateRecord data, Integer companyId){
        addressService.getPlaceIdApiMaps(data.addressRecord());
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
        orderModel.add(linkTo(methodOn(OrderController.class).findSellById(orderModel.getId())).withSelfRel());
        orderModel.getShoppingCartModel().getProductCartItems().forEach(product -> {
            product.add(linkTo(methodOn(OrderController.class).findSellById(orderModel.getId())).withSelfRel());
        });
        return orderModel;
    }

    public Page<OrderModel> findAllSells(Integer idCompany, Pageable pageable){
        CompanyModel companyModel = companyService.getCompanyById(idCompany);
        Page<OrderModel> sells = orderRepository.findOrderModelsByCompanyModel(companyModel, pageable);
        sells.forEach(orderModel -> orderModel.add(linkTo(methodOn(OrderController.class).findSellById(orderModel.getId())).withSelfRel()));
        sells.forEach(orderModel -> orderModel.getShoppingCartModel().getProductCartItems().forEach(product -> {
            product.add(linkTo(methodOn(OrderController.class).findSellById(orderModel.getId())).withSelfRel());
        }));
        return sells;
    }

    public OrderModel findByIdSell(Integer id){
        OrderModel orderModel = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        orderModel.add(linkTo(methodOn(OrderController.class).findSellById(orderModel.getId())).withSelfRel());
        orderModel.getShoppingCartModel().getProductCartItems().forEach(product -> {
            product.add(linkTo(methodOn(OrderController.class).findSellById(orderModel.getId())).withSelfRel());
        });
        return orderModel;
    }

    public void cancelSell(Integer id, Integer companyId){
        OrderModel orderModel = findByIdSell(id);
        verifyBelongCompany(orderModel, companyId);
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

    public Page<OrderModel> findByStatusOrder(StatusOrder statusOrder, Integer companyId, Pageable pageable){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        Page<OrderModel> orderModels = orderRepository.findByStatusOrderAndCompanyModel(statusOrder, companyModel, pageable);
        orderModels.forEach(orderModel -> orderModel.add(
                linkTo(methodOn(OrderController.class).findSellById(orderModel.getId())).withSelfRel()));
        orderModels.forEach(orderModel -> orderModel.getShoppingCartModel().getProductCartItems().forEach(product -> {
            product.add(linkTo(methodOn(OrderController.class).findSellById(orderModel.getId())).withSelfRel());
        }));
        return orderModels;
    }

    //Pedido em preparo
    public void setOrderPrepared(Integer id, Integer companyId){
        OrderModel orderModel = findByIdSell(id);
        verifyBelongCompany(orderModel, companyId);
        orderModel.setStatusOrder(StatusOrder.EmPreparo);
        orderRepository.save(orderModel);
    }

    //Set order Delivery
    public void setOrderDelivery(Integer idOrder, Integer motoboyId, Integer companyId){
        OrderModel orderModel = findByIdSell(idOrder);
        verifyBelongCompany(orderModel, companyId);
        if(orderModel.getTypeDelivery() == TypeDelivery.RETIRADA){
            throw new ResourceNotFoundException("Este pedido é para retirada, não é possível colocar o mesmo para entrega.");
        }
        deliveryService.setDeliveryInRoute(orderModel.getDeliveryModel(), motoboyId);
        orderModel.setStatusOrder(StatusOrder.Rota);
        orderRepository.save(orderModel);
    }

    public void setOrderReadyForCollect(Integer idOrder, Integer companyId){
        OrderModel orderModel = findByIdSell(idOrder);
        verifyBelongCompany(orderModel, companyId);
        if(orderModel.getTypeDelivery() == TypeDelivery.DELIVERY){
            throw new ResourceNotFoundException("Este pedido é para entrega, não é posível colocar para retirada!");
        }
        orderModel.setStatusOrder(StatusOrder.ProntoRetirada);
        orderRepository.save(orderModel);
    }

    public void setOrderFinished(Integer id, Integer companyId){
        OrderModel orderModel = findByIdSell(id);
        verifyBelongCompany(orderModel, companyId);
        if(orderModel.getTypeDelivery() == TypeDelivery.DELIVERY){
            deliveryService.setDeliveryFinished(orderModel.getDeliveryModel());
        }
        orderModel.setDateFinishOrder(LocalDateTime.now());
        orderModel.setStatusOrder(StatusOrder.Finalizado);
        orderRepository.save(orderModel);
    }

    public void verifyBelongCompany(OrderModel orderModel, Integer idCompany){
        if(!orderModel.getCompanyModel().getId().equals(idCompany)){
            throw new ResourceNotFoundException("Este pedido não pertence a esta empresa");
        }
    }

}
