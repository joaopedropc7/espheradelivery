package br.com.esphera.delivery.ApiMaps;

import java.util.List;

public record AddressMapDto(
        Integer revision,
        String postalCode,
        String administrativeArea,
        String locality,
        String subLocality,
        List<String> addressLines
) {
}
