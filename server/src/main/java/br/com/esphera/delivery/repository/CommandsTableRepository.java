package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CommandsTableModel;
import br.com.esphera.delivery.models.CompanyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CommandsTableRepository extends JpaRepository<CommandsTableModel, Integer> {

    Page<CommandsTableModel> findCommandsTableModelByCompanyModel(CompanyModel companyModel, Pageable pageable);

    Page<CommandsTableModel> findCommandsTableModelByDateCommandBetweenAndCompanyModel(LocalDate dateStart, LocalDate dateEnd, CompanyModel companyModel, Pageable pageable);



}
