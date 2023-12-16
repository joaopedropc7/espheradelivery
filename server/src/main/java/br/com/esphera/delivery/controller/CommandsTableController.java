package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.CommandsTableModel;
import br.com.esphera.delivery.models.DTOS.CommandsTableRecords.CommandsTableRecord;
import br.com.esphera.delivery.models.DTOS.CommandsTableRecords.RequestCommandsBetween;
import br.com.esphera.delivery.models.DTOS.responseDtos.CommandsResponseDTO;
import br.com.esphera.delivery.models.ProductCartItemModel;
import br.com.esphera.delivery.service.CommandsTableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandstable")
public class CommandsTableController {

    @Autowired
    private CommandsTableService commandsTableService;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Create commandsTable parsing table", description = "Create commandsTable parsing table", tags = {"Commands"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CommandsResponseDTO> createCommandsTable(CommandsTableRecord commandsTableRecord, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CommandsTableModel commandsTableModel = commandsTableService.createCommandsTable(commandsTableRecord, companyId);
        CommandsResponseDTO commandsResponseDTO = new CommandsResponseDTO(commandsTableModel);
        return ResponseEntity.ok().body(commandsResponseDTO);
    }

    @GetMapping("/{commandsTableId}")
    @Operation(summary = "Get commandsTable parsing commandTable id", description = "Get commandsTable parsing commandTable id", tags = {"Commands"})
    public ResponseEntity<CommandsResponseDTO> getCommandsTableById(@PathVariable(value = "commandsTableId") Integer commandsTableId){
        CommandsTableModel commandsTableModel = commandsTableService.getCommandsTableById(commandsTableId);
        CommandsResponseDTO commandsResponseDTO = new CommandsResponseDTO(commandsTableModel);
        return ResponseEntity.ok().body(commandsResponseDTO);
    }

    @GetMapping("/company")
    @Operation(summary = "Get all commandsTable company parsing company id", description = "Get all commandsTable company parsing company id", tags = {"Commands"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<CommandsResponseDTO>> getCommandsTableByCompany(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        var sortDirection = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "dateCommand"));
        Page<CommandsTableModel> commandsTableModels = commandsTableService.getCommandsTableByCompany(companyId, pageable);
        Page<CommandsResponseDTO> commandsResponseDTOS = CommandsResponseDTO.convert(commandsTableModels);
        return ResponseEntity.ok().body(commandsResponseDTOS);
    }

    @GetMapping("/date")
    @Operation(summary = "Get all commandsTable company parsing company id and period", description = "Get all commandsTable company parsing company id and period", tags = {"Commands"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<CommandsResponseDTO>> getCommandsTableByDateAndCompanyId(HttpServletRequest request, RequestCommandsBetween requestCommandsBetween, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        var sortDirection = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "dateCommand"));
        Page<CommandsTableModel> commandsTableModels = commandsTableService.getCommandsTableBetwennAndByCompany(companyId, requestCommandsBetween, pageable);
        Page<CommandsResponseDTO> commandsResponseDTOS = CommandsResponseDTO.convert(commandsTableModels);
        return ResponseEntity.ok().body(commandsResponseDTOS);
    }



}
