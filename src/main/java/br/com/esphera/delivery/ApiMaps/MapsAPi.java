package br.com.esphera.delivery.ApiMaps;

import br.com.esphera.delivery.ApiMaps.response.ResponseAddressValidationMapsAPI;
import br.com.esphera.delivery.ApiMaps.response.ResponseValidationDTO;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teste/maps")
public class MapsAPi {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseAddressValidationMapsAPI validateAddress(@RequestBody AddressRecord addressRecord) {
        String address = addressRecord.logradouro() + " " + addressRecord.numberHouse();
        List<String> addressLines = new ArrayList<>();
        addressLines.add(address);
        AddressMapDto addressMapDto = new AddressMapDto(0, addressRecord.cep(), addressRecord.UF(), addressRecord.localidade(), addressRecord.bairro(), addressLines);
        System.out.println(addressMapDto);
        ResponseAddressValidationMapsAPI responseAddressValidationMapsAPI = restTemplate.postForObject("https://addressvalidation.googleapis.com/v1:validateAddress?key=AIzaSyBqUk0RuC1TpLWLwYlKYbGU2EAZrOluNDA", addressMapDto, ResponseAddressValidationMapsAPI.class);
        return responseAddressValidationMapsAPI;
    }

}
