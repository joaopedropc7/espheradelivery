package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.DTOS.AddressRecord;
import br.com.esphera.delivery.models.DTOS.responseDtos.DeliveryConsultValueDTO;
import br.com.esphera.delivery.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/value/{companyId}")
    @Operation(summary = "Consult value delivery", description = "Consult value delivery", tags = {"Delivery"})
    public ResponseEntity<DeliveryConsultValueDTO> consultingValueDelivery(@PathVariable(value = "companyId") Integer companyId, @RequestBody AddressRecord addressRecord){
        DeliveryConsultValueDTO deliveryConsultValueDTO = deliveryService.consultValueDelivery(addressRecord, companyId);
        return ResponseEntity.ok().body(deliveryConsultValueDTO);
    }

}
