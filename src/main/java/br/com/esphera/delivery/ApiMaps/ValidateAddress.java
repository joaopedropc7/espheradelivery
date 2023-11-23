package br.com.esphera.delivery.ApiMaps;

public record ValidateAddress(
        AddressMapDto addressMapDto,
        String previousResponseId,
        Boolean enableUspsCass
        ) {
}
