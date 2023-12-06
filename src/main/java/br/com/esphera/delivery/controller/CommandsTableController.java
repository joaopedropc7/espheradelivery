package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.CommandsTableModel;
import br.com.esphera.delivery.models.DTOS.CommandsTableRecords.CommandsTableRecord;
import br.com.esphera.delivery.models.DTOS.CommandsTableRecords.RequestCommandsBetween;
import br.com.esphera.delivery.models.ProductCartItemModel;
import br.com.esphera.delivery.service.CommandsTableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandstable")
public class CommandsTableController {

    @Autowired
    private CommandsTableService commandsTableService;

    @PostMapping
    @Operation(summary = "Create commandsTable parsing table", description = "Create commandsTable parsing table",
            tags = {"Commands"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CommandsTableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<CommandsTableModel> createCommandsTable(CommandsTableRecord commandsTableRecord){
        CommandsTableModel commandsTableModel = commandsTableService.createCommandsTable(commandsTableRecord);
        return ResponseEntity.ok().body(commandsTableModel);
    }

    @GetMapping("/{commandsTableId}")
    @Operation(summary = "Get commandsTable parsing commandTable id", description = "Get commandsTable parsing commandTable id",
            tags = {"Commands"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CommandsTableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<CommandsTableModel> getCommandsTableById(@PathVariable(value = "commandsTableId") Integer commandsTableId){
        CommandsTableModel commandsTableModel = commandsTableService.getCommandsTableById(commandsTableId);
        return ResponseEntity.ok().body(commandsTableModel);
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get all commandsTable company parsing company id", description = "Get all commandsTable company parsing company id",
            tags = {"Commands"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CommandsTableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<CommandsTableModel>> getCommandsTableByCompany(@PathVariable(value = "companyId") Integer companyId){
        List<CommandsTableModel> commandsTableModels = commandsTableService.getCommandsTableByCompany(companyId);
        return ResponseEntity.ok().body(commandsTableModels);
    }

    @GetMapping("/date/{companyId}")
    @Operation(summary = "Get all commandsTable company parsing company id and period", description = "Get all commandsTable company parsing company id and period",
            tags = {"Commands"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CommandsTableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<CommandsTableModel>> getCommandsTableByDateAndCompanyId(@PathVariable(value = "companyId") Integer companyId, RequestCommandsBetween requestCommandsBetween){
        List<CommandsTableModel> commandsTableModels = commandsTableService.getCommandsTableBetwennAndByCompany(companyId, requestCommandsBetween);
        return ResponseEntity.ok().body(commandsTableModels);
    }


}
