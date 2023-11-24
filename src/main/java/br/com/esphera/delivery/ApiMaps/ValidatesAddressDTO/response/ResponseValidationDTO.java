package br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.response;

public record ResponseValidationDTO (
    AddressDTO address,
    GeoCode geocode
) {
}
