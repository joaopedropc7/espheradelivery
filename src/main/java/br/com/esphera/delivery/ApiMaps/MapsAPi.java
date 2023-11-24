package br.com.esphera.delivery.ApiMaps;

import br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.request.AddressMapDto;
import br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.request.ValidateAddress;
import br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.response.ResponseAddressValidationMapsAPI;
import br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.response.ResponseDistanceAPI;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/teste/maps")
public class MapsAPi {

    @Autowired
    private RestTemplate restTemplate;

    String postUrl = "https://addressvalidation.googleapis.com/v1:validateAddress?key=AIzaSyBqUk0RuC1TpLWLwYlKYbGU2EAZrOluNDA";

    @PostMapping
    public ResponseAddressValidationMapsAPI validateAddress(@RequestBody AddressRecord addressRecord) throws JsonProcessingException {
        String address = addressRecord.logradouro() + ", " + addressRecord.numberHouse();
        List<String> addressLines = new ArrayList<>();
        addressLines.add(address);
        AddressMapDto addressMapDto = new AddressMapDto(0, addressRecord.cep(), addressRecord.UF(), addressRecord.localidade(), addressRecord.bairro(), addressLines);
        ValidateAddress validateAddress = new ValidateAddress(addressMapDto, "", false);

        String json = new ObjectMapper().writeValueAsString(validateAddress);

        HttpEntity<String> entity = new HttpEntity<>(json);
        ResponseAddressValidationMapsAPI responseAddressValidationMapsAPI = restTemplate.exchange(postUrl, HttpMethod.POST, entity, ResponseAddressValidationMapsAPI.class).getBody();
        return responseAddressValidationMapsAPI;
    }

    @GetMapping("/{id1}/{id2}")
    public ResponseDistanceAPI getDistanceRouteDelivery(@PathVariable(value = "id1")String id1,@PathVariable(value = "id2")String id2){
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?destinations=place_id:" + id1 + "&origins=place_id:" + id2 + "&units=imperial&key=AIzaSyBqUk0RuC1TpLWLwYlKYbGU2EAZrOluNDA";
        ResponseDistanceAPI responseDistanceAPI = restTemplate.getForObject(url, ResponseDistanceAPI.class);
        return responseDistanceAPI;
    }


}
