package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyModel, Integer> {
}
