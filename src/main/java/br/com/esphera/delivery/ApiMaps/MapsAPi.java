package br.com.esphera.delivery.ApiMaps;

import br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.request.AddressMapDto;
import br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.request.ValidateAddress;
import br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.response.ResponseAddressValidationMapsAPI;
import br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.response.ResponseDistanceAPI;
import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MapsAPi {

    @Autowired
    private RestTemplate restTemplate;

    String postUrl = "https://addressvalidation.googleapis.com/v1:validateAddress?key=AIzaSyBqUk0RuC1TpLWLwYlKYbGU2EAZrOluNDA";


    public ResponseAddressValidationMapsAPI validateAddress(AddressRecord addressRecord) {
        String address = addressRecord.logradouro() + ", " + addressRecord.numberHouse();
        List<String> addressLines = new ArrayList<>();
        addressLines.add(address);
        AddressMapDto addressMapDto = new AddressMapDto(0, addressRecord.cep(), addressRecord.UF(), addressRecord.localidade(), addressRecord.bairro(), addressLines);
        ValidateAddress validateAddress = new ValidateAddress(addressMapDto, "", false);
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(validateAddress);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao converter para JSON.");
        }

        HttpEntity<String> entity = new HttpEntity<>(json);
        ResponseAddressValidationMapsAPI responseAddressValidationMapsAPI = restTemplate.exchange(postUrl, HttpMethod.POST, entity, ResponseAddressValidationMapsAPI.class).getBody();
        return responseAddressValidationMapsAPI;
    }

    public ResponseDistanceAPI getDistanceRouteDelivery(String localIdCompany, String localIdDestiny){
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?destinations=place_id:" + localIdCompany + "&origins=place_id:" + localIdDestiny + "&units=imperial&key=AIzaSyBqUk0RuC1TpLWLwYlKYbGU2EAZrOluNDA";
        ResponseDistanceAPI responseDistanceAPI = restTemplate.getForObject(url, ResponseDistanceAPI.class);
        return responseDistanceAPI;
    }


}
