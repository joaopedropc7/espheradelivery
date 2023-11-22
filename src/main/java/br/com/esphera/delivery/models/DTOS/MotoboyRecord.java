package br.com.esphera.delivery.models.DTOS;

import br.com.esphera.delivery.models.CompanyModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record MotoboyRecord(
        String nameMotoboy,
        String number,
        String email
) {
}
