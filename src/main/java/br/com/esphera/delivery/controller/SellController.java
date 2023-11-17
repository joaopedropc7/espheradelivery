package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.DTOS.SellCreateRecord;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.SellModel;
import br.com.esphera.delivery.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sell")
public class SellController {

    @Autowired
    private SellService sellService;

    @PostMapping
    public ResponseEntity<SellModel> createSell(@RequestBody SellCreateRecord data){
        SellModel sellCrated = sellService.createSell(data);
        return ResponseEntity.ok(sellCrated);
    }

    @GetMapping
    public ResponseEntity<List<SellModel>> findAllSells(){
        List<SellModel> sells = sellService.findAllSells();
        return ResponseEntity.ok(sells);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellModel> findSellById(@PathVariable(value = "id")Integer id){
        SellModel sellModel = sellService.findByIdSell(id);
        return ResponseEntity.ok(sellModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity cancelSell(@PathVariable(value = "id") Integer id){
        sellService.cancelSell(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{statusOrder}")
    public ResponseEntity<List<SellModel>> findByStatusOrder(@PathVariable(value = "statusOrder") StatusOrder statusOrder){
        List<SellModel> sells = sellService.findByStatusOrder(statusOrder);
        return ResponseEntity.ok(sells);
    }

    @PostMapping("/prepared")
    public ResponseEntity setOrderPrepared(Integer id){
        sellService.setOrderPrepared(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ready")
    public ResponseEntity setOrderReady(Integer id){
        sellService.setOrderReady(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/inroute")
    public ResponseEntity setOrderInRoute(Integer id){
        sellService.setOrderRoute(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delivered")
    public ResponseEntity setOrderDelivered(Integer id){
        sellService.setOrderPrepared(id);
        return ResponseEntity.ok().build();
    }

    
}
