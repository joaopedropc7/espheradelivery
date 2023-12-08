package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.DTOS.ProductEntryRecord;
import br.com.esphera.delivery.models.ProductEntryItemModel;
import br.com.esphera.delivery.models.ProductEntryModel;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.service.ProductEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entry")
public class EntryProductsController {

    @Autowired
    private ProductEntryService entryService;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Create a entry", description = "Create a entry parsing company ID and body",
            tags = {"Entry Products"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductEntryModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ProductEntryModel> createEntryProduct(@RequestBody ProductEntryRecord data, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        ProductEntryModel entryModel = entryService.createEntryProduct(data, companyId);
        return ResponseEntity.ok().body(entryModel);
    }

    @GetMapping("/find")
    @Operation(summary = "Find all entry", description = "Find all entrys",
            tags = {"Entry Products"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductEntryModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<ProductEntryModel>> getAllEntrys(HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        List<ProductEntryModel> entryModels = entryService.findByCompanyId(companyId);
        return ResponseEntity.ok().body(entryModels);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a entry", description = "Get a entry parsing company Id",
            tags = {"Entry Products"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductEntryModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ProductEntryModel> getEntryById(@PathVariable(value = "id") Integer id, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        ProductEntryModel entryModel = entryService.findById(id);
        return ResponseEntity.ok().body(entryModel);
    }

    @GetMapping("/products{entryId}")
    @Operation(summary = "Get all products in entry", description = "Get all products in entry",
            tags = {"Entry Products"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductEntryItemModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<ProductEntryItemModel>> getProductsInEntry(HttpServletRequest request, @PathVariable(value = "entryId") Integer entryId){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        List<ProductEntryItemModel> productsInEntry = entryService.findAllProductsInEntry(companyId, entryId);
        return ResponseEntity.ok().body(productsInEntry);
    }

    @PostMapping("/cancel/{id}")
    @Operation(summary = "Cancel entry", description = "Cancel entry by id",
            tags = {"Entry Products"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductEntryModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity cancelEntryById(@PathVariable Integer id, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        entryService.cancelEntryById(id);
        return ResponseEntity.noContent().build();
    }

}
