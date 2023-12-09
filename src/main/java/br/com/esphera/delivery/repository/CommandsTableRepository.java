package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.CommandsTableModel;
import br.com.esphera.delivery.models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CommandsTableRepository extends JpaRepository<CommandsTableModel, Integer> {

    List<CommandsTableModel> findCommandsTableModelByCompanyModel(CompanyModel companyModel);

    List<CommandsTableModel> findCommandsTableModelByDateCommandBetweenAndCompanyModel(LocalDate dateStart, LocalDate dateEnd, CompanyModel companyModel);

    @Query("SELECT COALESCE(MAX(p.idLocalCommand), 0) FROM CommandsTableModel p WHERE p.companyModel.id = :companyId")
    Integer findMaxIdLocalByCompany(@Param("companyId") Integer companyId);

    CommandsTableModel findCommandsTableModelByIdLocalCommandAndCompanyModel(Integer idLocalCommand, CompanyModel companyModel);

}
