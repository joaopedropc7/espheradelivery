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
    @Operation(summary = "Create a category", description = "Create a category",
            tags = {"Category"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CategoryModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRecord nameCategory, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CategoryModel category = categoryService.createCategory(nameCategory, companyId);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category);
        return  ResponseEntity.ok().body(categoryResponseDTO);
    }

    @GetMapping("/find/")
    @Operation(summary = "find categorys", description = "find categorys parsing id Company",
            tags = {"Category"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CategoryModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<CategoryResponseDTO>> findAllCategorys(HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        List<CategoryModel> categorys = categoryService.findAllCategorys(companyId);
        List<CategoryResponseDTO> categoryResponseDTOS = CategoryResponseDTO.convert(categorys);
        return ResponseEntity.ok().body(categoryResponseDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "find category by id", description = "find category by id",
            tags = {"Category"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CategoryModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable(value = "id") Integer id){
        CategoryModel category = categoryService.findById(id);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category);
        return ResponseEntity.ok().body(categoryResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Put category by id and body", description = "Put category by id and body",
            tags = {"Category"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CategoryModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable(value = "id")Integer categoryId, @RequestBody String categoryName, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CategoryModel category = categoryService.updateCategory(categoryId, categoryName, companyId);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category);
        return ResponseEntity.ok().body(categoryResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete category by id", description = "delete category by id",
            tags = {"Category"},
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
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity inactiveCategory(@PathVariable(value = "id")Integer id, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        categoryService.inactiveProduct(id, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("local/{idLocal}")
    @Operation(summary = "find category by idLocal", description = "find category by idLocal",
            tags = {"Category"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CategoryModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CategoryResponseDTO> findByLocalIdAndCompanyId(@PathVariable(value = "idLocal") Integer idLocal, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CategoryModel category = categoryService.findCategoryByIdLocalCategoryAndCompanyModel(idLocal, companyId);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category);
        return ResponseEntity.ok().body(categoryResponseDTO);
    }

}
