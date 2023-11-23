package br.com.esphera.delivery.ApiMaps.response;

public record ResponseValidationDTO (
    AddressDTO address,
    String placeId
) {
}
