package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, Integer> {
}
