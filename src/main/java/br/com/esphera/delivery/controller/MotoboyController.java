package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.MotoboyRecord;
import br.com.esphera.delivery.models.MotoboysModel;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.service.MotoboyService;
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
@RequestMapping("/api/motoboy")
public class MotoboyController {

    @Autowired
    private MotoboyService motoboyService;

    @PostMapping("/{companyId}")
    @Operation(summary = "Create motoboy", description = "Create motoboy",
            tags = {"Motoboy"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MotoboysModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<MotoboysModel> createMotoboy(@PathVariable(value = "companyId")Integer companyId, @RequestBody MotoboyRecord motoboyRecord){
        MotoboysModel motoboysModel = motoboyService.createMotoboy(motoboyRecord, companyId);
        return ResponseEntity.ok(motoboysModel);
    };

    @GetMapping("findall/{companyId}")
    @Operation(summary = "Get all motoboys in company", description = "Get all motoboys in company",
            tags = {"Motoboy"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MotoboysModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<MotoboysModel>> findMotoboysByIdCompany(@PathVariable(value = "companyId")Integer companyId){
        List<MotoboysModel> motoboys = motoboyService.findAllMotoboysByCompanyId(companyId);
        return ResponseEntity.ok(motoboys);
    }

    @GetMapping("/{motoboyId}")
    @Operation(summary = "Get motoboy by Id", description = "Get motoboy by Id",
            tags = {"Motoboy"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MotoboysModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<MotoboysModel> findMotoboyById(@PathVariable(value = "motoboyId")Integer motoboyId){
        MotoboysModel motoboysModel = motoboyService.findMotoboyById(motoboyId);
        return ResponseEntity.ok(motoboysModel);
    }

    @PutMapping("/{motoboyId}")
    @Operation(summary = "Put motoboy parsing Id and body", description = "Put motoboy parsing Id and body",
            tags = {"Motoboy"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MotoboysModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<MotoboysModel> putMotoboy(@PathVariable(value = "motoboyId")Integer motoboyId, @RequestBody MotoboyRecord motoboyRecord){
        MotoboysModel motoboysModel = motoboyService.putMotoboy(motoboyId, motoboyRecord);
        return ResponseEntity.ok(motoboysModel);
    }

    @PutMapping("/active/{motoboyId}")
    @Operation(summary = "Active motoboy parsing Id", description = "Active motoboy parsing Id",
            tags = {"Motoboy"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = void.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity activeMotoboy(@PathVariable(value = "motoboyId")Integer motoboyId){
         motoboyService.activeMotoboy(motoboyId);
         return ResponseEntity.ok().build();
    }

    @PutMapping("/inactive/{motoboyId}")
    @Operation(summary = "Inactive motoboy parsing Id", description = "Inactive motoboy parsing Id",
            tags = {"Motoboy"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = void.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity inactiveMotoboy(@PathVariable(value = "motoboyId")Integer motoboyId){
        motoboyService.inactiveMotoboy(motoboyId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{motoboyId}")
    @Operation(summary = "Delete motoboy parsing Id", description = "Delete motoboy parsing Id",
            tags = {"Motoboy"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = void.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity deleteMotoboy(@PathVariable(value = "motoboyId")Integer motoboyId){
        motoboyService.deleteMotoboy(motoboyId);
        return ResponseEntity.noContent().build();
    }




}
