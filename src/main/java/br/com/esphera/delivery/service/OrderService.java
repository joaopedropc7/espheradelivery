package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.EnderecoModel;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.OrderModel;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.repository.AddressRepository;
import br.com.esphera.delivery.repository.OrderRepository;
import br.com.esphera.delivery.repository.ShoppingCartRepository;
import br.com.esphera.delivery.validations.sells.ValidationSales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private List<ValidationSales> validationSales;

    @Autowired
    private ProductService productService;

    public OrderModel createSell(OrderCreateRecord data){
        ShoppingCartModel shoppingCartModel = shoppingCartRepository.findById(data.shoppingCartId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        EnderecoModel enderecoModel = new EnderecoModel(data.addressRecord());
        validationSales.forEach(validationSales -> validationSales.valid(data, shoppingCartModel));
        addressRepository.save(enderecoModel);
        OrderModel orderModel = new OrderModel(data, enderecoModel, shoppingCartModel);
        orderRepository.save(orderModel);
        shoppingCartModel.getProductCartItems().forEach(productCartItemModel -> {
            productService.sellProduct(productCartItemModel.getProduct().getId(), productCartItemModel.getQuantity());
        });
        return orderModel;
    }

    public List<OrderModel> findAllSells(){
        List<OrderModel> sells = orderRepository.findAll();
        return sells;
    }

    public OrderModel findByIdSell(Integer id){
        OrderModel orderModel = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        return orderModel;
    }

    public void cancelSell(Integer id){
        OrderModel orderModel = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        orderModel.setOrderCancelled(true);
        orderModel.setStatusOrder(StatusOrder.Cancelado);
        orderRepository.save(orderModel);
    }

    public List<OrderModel> findByStatusOrder(StatusOrder statusOrder){
        List<OrderModel> orderModels = orderRepository.findByStatusOrder(statusOrder);
        return orderModels;
    }

    //Pedido em preparo
    public void setOrderPrepared(Integer id){
        OrderModel orderModel = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        orderModel.setStatusOrder(StatusOrder.EmPreparo);
        orderRepository.save(orderModel);
    }

    //Pedido pronto para Entrega/Retirada
    public void setOrderReady(Integer id){
        OrderModel orderModel = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        orderModel.setStatusOrder(StatusOrder.Pronto);
        orderRepository.save(orderModel);
    }

    //Pedido em rota de entrega
    public void setOrderRoute(Integer id){
        OrderModel orderModel = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        orderModel.setStatusOrder(StatusOrder.Rota);
        orderRepository.save(orderModel);
    }

    //Pedido entregue/retirado
    public void setOrderDelivered(Integer id){
        OrderModel orderModel = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        orderModel.setStatusOrder(StatusOrder.Entregue);
        orderRepository.save(orderModel);
    }

    public void setOrderFinished(Integer id){
        OrderModel orderModel = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        orderModel.setStatusOrder(StatusOrder.Finalizado);
        orderRepository.save(orderModel);
    }



}
