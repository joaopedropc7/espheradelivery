package br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.request;


public record ValidateAddress(
        AddressMapDto address,
        String previousResponseId,
        Boolean enableUspsCass
        ) {
}
