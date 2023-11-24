package br.com.esphera.delivery.ApiMaps.ValidatesAddressDTO.response;

public record Elements(
        DistanceDistanceAPIDTO distance,
        DurationDistanceDTO duration,
        String status
) {
}
