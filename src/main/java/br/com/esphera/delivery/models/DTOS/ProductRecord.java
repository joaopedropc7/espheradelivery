package br.com.esphera.delivery.models.DTOS;

public record ProductRecord(String name, Integer idCategory, String description, String image, Double costValue, Double valueSell) {
}
