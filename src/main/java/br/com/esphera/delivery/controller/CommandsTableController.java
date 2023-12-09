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
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CommandsResponseDTO> createCommandsTable(CommandsTableRecord commandsTableRecord, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CommandsTableModel commandsTableModel = commandsTableService.createCommandsTable(commandsTableRecord, companyId);
        CommandsResponseDTO commandsResponseDTO = new CommandsResponseDTO(commandsTableModel);
        return ResponseEntity.ok().body(commandsResponseDTO);
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
    public ResponseEntity<CommandsResponseDTO> getCommandsTableById(@PathVariable(value = "commandsTableId") Integer commandsTableId){
        CommandsTableModel commandsTableModel = commandsTableService.getCommandsTableById(commandsTableId);
        CommandsResponseDTO commandsResponseDTO = new CommandsResponseDTO(commandsTableModel);
        return ResponseEntity.ok().body(commandsResponseDTO);
    }

    @GetMapping("/company")
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
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<CommandsResponseDTO>> getCommandsTableByCompany(HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        List<CommandsTableModel> commandsTableModels = commandsTableService.getCommandsTableByCompany(companyId);
        List<CommandsResponseDTO> commandsResponseDTOS = CommandsResponseDTO.convert(commandsTableModels);
        return ResponseEntity.ok().body(commandsResponseDTOS);
    }

    @GetMapping("/date")
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
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<CommandsResponseDTO>> getCommandsTableByDateAndCompanyId(HttpServletRequest request, RequestCommandsBetween requestCommandsBetween){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        List<CommandsTableModel> commandsTableModels = commandsTableService.getCommandsTableBetwennAndByCompany(companyId, requestCommandsBetween);
        List<CommandsResponseDTO> commandsResponseDTOS = CommandsResponseDTO.convert(commandsTableModels);
        return ResponseEntity.ok().body(commandsResponseDTOS);
    }

    @GetMapping("/local/{commandsTableLocalId}")
    @Operation(summary = "Get commandsTable parsing commandTable  localId", description = "Get commandsTable parsing commandTable localId",
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
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CommandsResponseDTO> getCommandsTableByLocalId(@PathVariable(value = "commandsTableLocalId") Integer commandsTableId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CommandsTableModel commandsTableModel = commandsTableService.getCommandByLocalIdAndCompany(commandsTableId, companyId);
        CommandsResponseDTO commandsResponseDTO = new CommandsResponseDTO(commandsTableModel);
        return ResponseEntity.ok().body(commandsResponseDTO);
    }

}
