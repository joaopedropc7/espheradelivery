package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.ExpensesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpensesRepository extends JpaRepository<ExpensesModel, Integer> {
}
