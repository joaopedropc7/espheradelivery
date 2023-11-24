package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import br.com.esphera.delivery.models.DTOS.DeliveryConsultValueDTO;
import br.com.esphera.delivery.models.DTOS.DistanceDurationDTO;
import br.com.esphera.delivery.service.AddressService;
import br.com.esphera.delivery.service.CompanyService;
import br.com.esphera.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;



    @PostMapping("/value/{companyId}]")
    public DeliveryConsultValueDTO consultingValueDelivery(@PathVariable(value = "companyId") Integer companyId, @RequestBody AddressRecord addressRecord){
        DeliveryConsultValueDTO deliveryConsultValueDTO = deliveryService.consultValueDelivery(addressRecord, companyId);
        return deliveryConsultValueDTO;
    }

}
