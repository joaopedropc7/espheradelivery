package br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.response;

import java.util.List;

public record ResponseDistanceAPI(
        List<String> destination_addresses,
        List<String> origin_addresses,
        List<Rows> rows,
        String status
){
}
