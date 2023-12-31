package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.CompanyRecord;
import br.com.esphera.delivery.models.DTOS.CompanyUpdateRecord;
import br.com.esphera.delivery.models.DTOS.AddressRecord;
import br.com.esphera.delivery.models.DTOS.responseDtos.CompanyInfoResponseDTO;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.service.CompanyService;
import br.com.esphera.delivery.service.ConfigsCompanyService;
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
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private FIleController fIleController;
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
    public ResponseEntity<Page<CompanyModel>> getAllCompanies(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nomeFantasia"));
        Page<CompanyModel> companyModelList = companyService.getAllCompanies(pageable);
        return ResponseEntity.ok().body(companyModelList);
    }

    @GetMapping("/find/{name}")
    @Operation(summary = "Get company by name", description = "Get company by name",
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
    public ResponseEntity<Page<CompanyModel>> getCompaniesByName(@PathVariable(value = "name")String name ,@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nomeFantasia"));
        Page<CompanyModel> companyModelList = companyService.findCompaniesByNomeFantasia(name ,pageable);
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

    @GetMapping("/info")
    @Operation(summary = "Get a company info by id", description = "Get a company info by id",
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
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CompanyInfoResponseDTO> getCompanyInfoById(HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CompanyModel companyModel = companyService.getCompanyById(companyId);

        CompanyInfoResponseDTO companyInfoResponseDTO = new CompanyInfoResponseDTO(companyModel);
        return ResponseEntity.ok().body(companyInfoResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Put a company by id and body", description = "Put a company by id and body", tags = {"Company"})
    public ResponseEntity<CompanyModel> putCompany(@PathVariable(value = "id") Integer id, CompanyUpdateRecord companyUpdateRecord){
        CompanyModel companyModel = companyService.putCompany(id, companyUpdateRecord);
        return ResponseEntity.ok().body(companyModel);
    }

    @PutMapping("/address/{id}")
    @Operation(summary = "Put a company address by id and body", description = "Put a company address by id and body", tags = {"Company"})
    public ResponseEntity<CompanyModel> putCompanyAddress(@PathVariable(value = "id") Integer id, AddressRecord addressRecord){
        CompanyModel companyModel = companyService.putCompanyAddress(id, addressRecord);
        return ResponseEntity.ok().body(companyModel);
    }

    @PutMapping("/inactive/{id}")
    @Operation(summary = "Inactive a company address by id", description = "Inactive a company address by id", tags = {"Company"})
    public ResponseEntity inactiveCompany(@PathVariable(value = "id") Integer id){
        companyService.inactiveCompany(id);
        return ResponseEntity.noContent().build();
    }



}
