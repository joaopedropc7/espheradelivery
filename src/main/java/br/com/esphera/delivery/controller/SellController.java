package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.DTOS.SellCreateRecord;
import br.com.esphera.delivery.models.SellModel;
import br.com.esphera.delivery.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    
}
