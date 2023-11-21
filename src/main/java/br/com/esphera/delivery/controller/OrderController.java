package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.OrderModel;
import br.com.esphera.delivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sell")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderModel> createSell(@RequestBody OrderCreateRecord data){
        OrderModel sellCrated = orderService.createSell(data);
        return ResponseEntity.ok(sellCrated);
    }

    @GetMapping
    public ResponseEntity<List<OrderModel>> findAllSells(){
        List<OrderModel> sells = orderService.findAllSells();
        return ResponseEntity.ok(sells);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> findSellById(@PathVariable(value = "id")Integer id){
        OrderModel orderModel = orderService.findByIdSell(id);
        return ResponseEntity.ok(orderModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity cancelSell(@PathVariable(value = "id") Integer id){
        orderService.cancelSell(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{statusOrder}")
    public ResponseEntity<List<OrderModel>> findByStatusOrder(@PathVariable(value = "statusOrder") StatusOrder statusOrder){
        List<OrderModel> sells = orderService.findByStatusOrder(statusOrder);
        return ResponseEntity.ok(sells);
    }

    @PostMapping("/prepared")
    public ResponseEntity setOrderPrepared(Integer id){
        orderService.setOrderPrepared(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ready")
    public ResponseEntity setOrderReady(Integer id){
        orderService.setOrderReady(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/inroute")
    public ResponseEntity setOrderInRoute(Integer id){
        orderService.setOrderRoute(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delivered")
    public ResponseEntity setOrderDelivered(Integer id){
        orderService.setOrderPrepared(id);
        return ResponseEntity.ok().build();
    }

    
}
