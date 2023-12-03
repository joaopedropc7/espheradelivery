package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CommandsTableModel;
import br.com.esphera.delivery.models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CommandsTableRepository extends JpaRepository<CommandsTableModel, Integer> {

    List<CommandsTableModel> findCommandsTableModelByCompanyModel(CompanyModel companyModel);

    List<CommandsTableModel> findCommandsTableModelByDateCommandBetweenAndCompanyModel(LocalDate dateStart, LocalDate dateEnd, CompanyModel companyModel);

}
