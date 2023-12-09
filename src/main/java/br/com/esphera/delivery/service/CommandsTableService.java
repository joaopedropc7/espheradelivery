package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CommandsTableModel;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.CommandsTableRecords.CommandsTableRecord;
import br.com.esphera.delivery.models.DTOS.CommandsTableRecords.RequestCommandsBetween;
import br.com.esphera.delivery.models.TableModel;
import br.com.esphera.delivery.repository.CommandsTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommandsTableService {

    @Autowired
    private CommandsTableRepository commandsTableRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TableService tableService;

    @Autowired
    private ProductService productService;

    public CommandsTableModel createCommandsTable(CommandsTableRecord commandsTableRecord, Integer companyId) {
        Integer lastIdInsert = commandsTableRepository.findMaxIdLocalByCompany(companyId);
        TableModel tableModel = tableService.getTableById(commandsTableRecord.tableId());
        CommandsTableModel commandsTableModel = new CommandsTableModel(tableModel.getCompanyModel(), tableModel, commandsTableRecord.paymentsMethod(), commandsTableRecord.observation(), lastIdInsert);
        tableService.clearTable(tableModel.getId());
        commandsTableModel.getProductsTable().stream().forEach(productTable -> {
            productService.sellProduct(productTable.getProduct(), productTable.getQuantity());
        });
        return commandsTableRepository.save(commandsTableModel);
    }

    public CommandsTableModel getCommandsTableById(Integer commandsTableId) {
        return commandsTableRepository.findById(commandsTableId).orElseThrow(() -> new ResourceNotFoundException("Comanda não encontrada!"));
    }

    public List<CommandsTableModel> getCommandsTableByCompany(Integer companyId) {
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        return commandsTableRepository.findCommandsTableModelByCompanyModel(companyModel);
    }

   public List<CommandsTableModel> getCommandsTableBetwennAndByCompany(Integer companyId, RequestCommandsBetween dtoRequest){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        LocalDate dateStart = LocalDate.parse(dtoRequest.dateStart());
        LocalDate dateEnd = LocalDate.parse(dtoRequest.dateEnd());
        return commandsTableRepository.findCommandsTableModelByDateCommandBetweenAndCompanyModel(dateStart, dateEnd, companyModel);
   }

   public CommandsTableModel getCommandByLocalIdAndCompany(Integer idLocalCommand, Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        return commandsTableRepository.findCommandsTableModelByIdLocalCommandAndCompanyModel(idLocalCommand, companyModel);
   }

   public void verifyCommandsTableBelongsCompany(CommandsTableModel commandsTableModel, Integer companyId){
       if(!commandsTableModel.getCompanyModel().getId().equals(companyId)){
           throw new ResourceNotFoundException("Comanda não pertence a empresa!");
       }
   }


}
