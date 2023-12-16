package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.MotoboyRecord;
import br.com.esphera.delivery.models.DTOS.responseDtos.MotoboyResponseDTO;
import br.com.esphera.delivery.models.MotoboysModel;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.service.MotoboyService;
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
@RequestMapping("/api/motoboy")
public class MotoboyController {

    @Autowired
    private MotoboyService motoboyService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Create motoboy", description = "Create motoboy", tags = {"Motoboy"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<MotoboyResponseDTO> createMotoboy(@RequestBody MotoboyRecord motoboyRecord, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        MotoboysModel motoboysModel = motoboyService.createMotoboy(motoboyRecord, companyId);
        MotoboyResponseDTO motoboyResponseDTO = new MotoboyResponseDTO(motoboysModel);
        return ResponseEntity.ok(motoboyResponseDTO);
    };

    @GetMapping("/findByName/{name}")
    @Operation(summary = "Get all motoboys by name", description = "Get all motoboys by name", tags = {"Motoboy"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<MotoboyResponseDTO>> findMotoboysByName(@PathVariable(value = "name")String name,HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nameMotoboy"));
        Page<MotoboysModel> motoboys = motoboyService.getMotoboysByNameAndCompanyId(name ,companyId, pageable);
        Page<MotoboyResponseDTO> motoboyResponseDTO = MotoboyResponseDTO.convert(motoboys);
        return ResponseEntity.ok(motoboyResponseDTO);
    }

    @GetMapping("/findall")
    @Operation(summary = "Get all motoboys in company", description = "Get all motoboys in company", tags = {"Motoboy"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<MotoboyResponseDTO>> findMotoboysByIdCompany(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nameMotoboy"));
        Page<MotoboysModel> motoboys = motoboyService.findAllMotoboysByCompanyId(companyId, pageable);
        Page<MotoboyResponseDTO> motoboyResponseDTO = MotoboyResponseDTO.convert(motoboys);
        return ResponseEntity.ok(motoboyResponseDTO);
    }

    @GetMapping("/{motoboyId}")
    @Operation(summary = "Get motoboy by Id", description = "Get motoboy by Id", tags = {"Motoboy"})
    public ResponseEntity<MotoboyResponseDTO> findMotoboyById(@PathVariable(value = "motoboyId")Integer motoboyId){
        MotoboysModel motoboysModel = motoboyService.findMotoboyById(motoboyId);
        MotoboyResponseDTO motoboyResponseDTO = new MotoboyResponseDTO(motoboysModel);
        return ResponseEntity.ok(motoboyResponseDTO);
    }

    @PutMapping("/{motoboyId}")
    @Operation(summary = "Put motoboy parsing Id and body", description = "Put motoboy parsing Id and body", tags = {"Motoboy"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<MotoboyResponseDTO> putMotoboy(@PathVariable(value = "motoboyId")Integer motoboyId, @RequestBody MotoboyRecord motoboyRecord, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        MotoboysModel motoboysModel = motoboyService.putMotoboy(motoboyId, motoboyRecord, companyId);
        MotoboyResponseDTO motoboyResponseDTO = new MotoboyResponseDTO(motoboysModel);
        return ResponseEntity.ok(motoboyResponseDTO);
    }

    @PutMapping("/active/{motoboyId}")
    @Operation(summary = "Active motoboy parsing Id", description = "Active motoboy parsing Id", tags = {"Motoboy"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity activeMotoboy(@PathVariable(value = "motoboyId")Integer motoboyId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
         motoboyService.activeMotoboy(motoboyId, companyId);
         return ResponseEntity.ok().build();
    }

    @PutMapping("/inactive/{motoboyId}")
    @Operation(summary = "Inactive motoboy parsing Id", description = "Inactive motoboy parsing Id", tags = {"Motoboy"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity inactiveMotoboy(@PathVariable(value = "motoboyId")Integer motoboyId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        motoboyService.inactiveMotoboy(motoboyId, companyId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{motoboyId}")
    @Operation(summary = "Delete motoboy parsing Id", description = "Delete motoboy parsing Id", tags = {"Motoboy"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity deleteMotoboy(@PathVariable(value = "motoboyId")Integer motoboyId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        motoboyService.deleteMotoboy(motoboyId, companyId);
        return ResponseEntity.noContent().build();
    }




}
