package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.CompanyRecord;
import br.com.esphera.delivery.models.DTOS.CompanyUpdateRecord;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.service.CompanyService;
import br.com.esphera.delivery.service.ConfigsCompanyService;
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
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ConfigsCompanyService configsCompanyService;

    @PostMapping
    @Operation(summary = "Create a company", description = "Create a company",
            tags = {"Company"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CompanyModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<CompanyModel> createCompany(@RequestBody CompanyRecord companyRecord){
        CompanyModel companyModel = companyService.createCompany(companyRecord);
        configsCompanyService.createConfigForCompany(companyModel);
        return ResponseEntity.ok().body(companyModel);
    }

    @GetMapping
    @Operation(summary = "Get all company", description = "Get all company",
            tags = {"Company"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<CompanyModel>> getAllCompanies(){
        List<CompanyModel> companyModelList = companyService.getAllCompanies();
        return ResponseEntity.ok().body(companyModelList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a company by id", description = "Get a company by id",
            tags = {"Company"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<CompanyModel> getCompanyById(@PathVariable(value = "id") Integer id){
        CompanyModel companyModel = companyService.getCompanyById(id);
        return ResponseEntity.ok().body(companyModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Put a company by id and body", description = "Put a company by id and body",
            tags = {"Company"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<CompanyModel> putCompany(@PathVariable(value = "id") Integer id, CompanyUpdateRecord companyUpdateRecord){
        CompanyModel companyModel = companyService.putCompany(id, companyUpdateRecord);
        return ResponseEntity.ok().body(companyModel);
    }

    @PutMapping("/address/{id}")
    @Operation(summary = "Put a company address by id and body", description = "Put a company address by id and body",
            tags = {"Company"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ProductModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<CompanyModel> putCompanyAddress(@PathVariable(value = "id") Integer id, AddressRecord addressRecord){
        CompanyModel companyModel = companyService.putCompanyAddress(id, addressRecord);
        return ResponseEntity.ok().body(companyModel);
    }

    @PutMapping("/inactive/{id}")
    @Operation(summary = "Inactive a company address by id", description = "Inactive a company address by id",
            tags = {"Company"},
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
    public ResponseEntity inactiveCompany(@PathVariable(value = "id") Integer id){
        companyService.inactiveCompany(id);
        return ResponseEntity.noContent().build();
    }



}
