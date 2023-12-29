package br.com.esphera.delivery.service;

import br.com.esphera.delivery.controller.CommandsTableController;
import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CommandsTableModel;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.CommandsTableRecords.CommandsTableRecord;
import br.com.esphera.delivery.models.DTOS.CommandsTableRecords.RequestCommandsBetween;
import br.com.esphera.delivery.models.TableModel;
import br.com.esphera.delivery.repository.CommandsTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;


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
        TableModel tableModel = tableService.getTableById(commandsTableRecord.tableId());
        CommandsTableModel commandsTableModel = new CommandsTableModel(tableModel.getCompanyModel(), tableModel, commandsTableRecord.paymentsMethod(), commandsTableRecord.observation());
        tableService.clearTable(tableModel.getId());
        commandsTableModel.getProductsTable().stream().forEach(productTable -> {
            productService.sellProduct(productTable.getProduct(), productTable.getQuantity(), productTable.getTotalValue());
        });
       commandsTableRepository.save(commandsTableModel);
       commandsTableModel.add(linkTo(methodOn(CommandsTableController.class).getCommandsTableById(commandsTableModel.getId())).withSelfRel());
       return commandsTableModel;
    }

    public CommandsTableModel getCommandsTableById(Integer commandsTableId) {
        CommandsTableModel commandsTableModel = commandsTableRepository.findById(commandsTableId).orElseThrow(() -> new ResourceNotFoundException("Comanda não encontrada!"));
        commandsTableModel.add(linkTo(methodOn(CommandsTableController.class).getCommandsTableById(commandsTableModel.getId())).withSelfRel());
        return commandsTableModel;
    }

    public Page<CommandsTableModel> getCommandsTableByCompany(Integer companyId, Pageable pageable) {
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        Page<CommandsTableModel> commandsTableModel = commandsTableRepository.findCommandsTableModelByCompanyModel(companyModel, pageable);
        commandsTableModel.stream().forEach(commandsTable -> commandsTable.add(linkTo(methodOn(CommandsTableController.class).getCommandsTableById(commandsTable.getId())).withSelfRel()));
        return commandsTableModel;
    }

   public Page<CommandsTableModel> getCommandsTableBetwennAndByCompany(Integer companyId, RequestCommandsBetween dtoRequest, Pageable pageable){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        LocalDate dateStart = LocalDate.parse(dtoRequest.dateStart());
        LocalDate dateEnd = LocalDate.parse(dtoRequest.dateEnd());
        Page<CommandsTableModel> commands = commandsTableRepository.findCommandsTableModelByDateCommandBetweenAndCompanyModel(dateStart, dateEnd, companyModel, pageable);
        commands.stream().forEach(commandsTable -> commandsTable.add(linkTo(methodOn(CommandsTableController.class).getCommandsTableById(commandsTable.getId())).withSelfRel()));
        return commands;
    }


   public void verifyCommandsTableBelongsCompany(CommandsTableModel commandsTableModel, Integer companyId){
       if(!commandsTableModel.getCompanyModel().getId().equals(companyId)){
           throw new ResourceNotFoundException("Comanda não pertence a empresa!");
       }
   }


}
