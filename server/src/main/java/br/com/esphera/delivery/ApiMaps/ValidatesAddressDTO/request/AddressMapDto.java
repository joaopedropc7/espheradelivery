package br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.request;

import java.util.List;

public record AddressMapDto(
        Integer revision,
        String postalCode,
        String administrativeArea,
        String locality,
        String sublocality,
        List<String> addressLines
) {
}
