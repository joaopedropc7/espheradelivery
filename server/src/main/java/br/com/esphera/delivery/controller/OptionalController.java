package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.DTOS.OptionalDTO;
import br.com.esphera.delivery.models.DTOS.responseDtos.OptionalResponseDTO;
import br.com.esphera.delivery.models.OptionalModel;
import br.com.esphera.delivery.service.OptionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Optional", description = "Optional API")
@RestController
@RequestMapping("/api/optional")
public class OptionalController {

    @Autowired
    private OptionalService optionalService;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Create optional", description = "Create optional", tags = {"Optional"})
    public OptionalResponseDTO createOptional(HttpServletRequest httpServletRequest, OptionalDTO optionalDTO){
        OptionalResponseDTO optionalResponseDTO = optionalService.createOptional(tokenService.getCompanyIdFromToken(tokenService.recoverToken(httpServletRequest)), optionalDTO);
        return optionalResponseDTO;
    }

    @GetMapping("/{optionalId}")
    @Operation(summary = "Get optional by id", description = "Get optional by id", tags = {"Optional"})
    public OptionalResponseDTO getOptionalById(@PathVariable(value = "optionalId") Integer optionalId){
        OptionalModel optionalModel = optionalService.getOptionalById(optionalId);
        OptionalResponseDTO optionalResponseDTO = new OptionalResponseDTO(optionalModel);
        return optionalResponseDTO;
    }

    @GetMapping
    @Operation(summary = "Get all optionals by company id", description = "Get all optionals by company id", tags = {"Optional"})
    public Page<OptionalResponseDTO> getAllOptionalsByCompanyId(HttpServletRequest httpServletRequest, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));
        Page<OptionalModel> optionalModels = optionalService.getAllOptionalsByCompanyId(tokenService.getCompanyIdFromToken(tokenService.recoverToken(httpServletRequest)), pageable);
        Page<OptionalResponseDTO> optionalResponseDTOS = OptionalResponseDTO.convert(optionalModels);
        return optionalResponseDTOS;
    }

    @PutMapping("/{optionalId}")
    @Operation(summary = "Update optional", description = "Update optional", tags = {"Optional"})
    public OptionalResponseDTO updateOptional(@PathVariable(value = "optionalId") Integer optionalId, OptionalDTO optionalDTO){
        OptionalResponseDTO optionalResponseDTO = optionalService.updateOptional(optionalId, optionalDTO);
        return optionalResponseDTO;
    }



}
