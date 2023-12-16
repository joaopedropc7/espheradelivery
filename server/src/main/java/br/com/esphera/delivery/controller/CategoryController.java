package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.CategoryModel;
import br.com.esphera.delivery.models.DTOS.CategoryRecord;
import br.com.esphera.delivery.models.DTOS.responseDtos.CategoryResponseDTO;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.service.CategoryService;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Create a category", description = "Create a category", tags = {"Category"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRecord nameCategory, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CategoryModel category = categoryService.createCategory(nameCategory, companyId);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category);
        return  ResponseEntity.ok().body(categoryResponseDTO);
    }

    @GetMapping("/find")
    @Operation(summary = "find categorys", description = "find categorys parsing id Company", tags = {"Category"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<CategoryResponseDTO>> findAllCategorys(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "categoryName"));
        Page<CategoryModel> categorys = categoryService.findAllCategorys(companyId, pageable);
        Page<CategoryResponseDTO> categoryResponseDTOS = CategoryResponseDTO.convert(categorys);
        return ResponseEntity.ok().body(categoryResponseDTOS);
    }

    @GetMapping("/findByName/{name}")
    @Operation(summary = "find categorys by name", description = "find categorys by name", tags = {"Category"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<CategoryResponseDTO>> findCategorysByName(@PathVariable(value = "name")String name, HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "categoryName"));
        Page<CategoryModel> categorys = categoryService.findCategorysByName(name ,companyId, pageable);
        Page<CategoryResponseDTO> categoryResponseDTOS = CategoryResponseDTO.convert(categorys);
        return ResponseEntity.ok().body(categoryResponseDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "find category by id", description = "find category by id", tags = {"Category"})
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable(value = "id") Integer id){
        CategoryModel category = categoryService.findById(id);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category);
        return ResponseEntity.ok().body(categoryResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Put category by id and body", description = "Put category by id and body", tags = {"Category"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable(value = "id")Integer categoryId, @RequestBody String categoryName, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CategoryModel category = categoryService.updateCategory(categoryId, categoryName, companyId);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category);
        return ResponseEntity.ok().body(categoryResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete category by id", description = "delete category by id", tags = {"Category"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity inactiveCategory(@PathVariable(value = "id")Integer id, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        categoryService.inactiveProduct(id, companyId);
        return ResponseEntity.noContent().build();
    }



}
