package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.CategoryModel;
import br.com.esphera.delivery.models.DTOS.CategoryRecord;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.service.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/{companyId}")
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
    public ResponseEntity<CategoryModel> createCategory(@RequestBody CategoryRecord nameCategory, @PathVariable(value = "companyId") Integer companyId){
        CategoryModel categoryModel = categoryService.createCategory(nameCategory, companyId);
        return  ResponseEntity.ok().body(categoryModel);
    }

    @GetMapping("/find/{idCompany}")
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
    public ResponseEntity<List<CategoryModel>> findAllCategorys(@PathVariable(value = "idCompany")Integer idCompany){
        List<CategoryModel> categorys = categoryService.findAllCategorys(idCompany);
        return ResponseEntity.ok().body(categorys);
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
    public ResponseEntity<CategoryModel> findById(@PathVariable(value = "id") Integer id){
        CategoryModel categoryModel = categoryService.findById(id);
        return ResponseEntity.ok().body(categoryModel);
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
    public ResponseEntity<CategoryModel> updateCategory(@PathVariable(value = "id")Integer categoryId, @RequestBody String categoryName){
        CategoryModel categoryModel = categoryService.updateCategory(categoryId, categoryName);
        return ResponseEntity.ok().body(categoryModel);
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
    public ResponseEntity inactiveCategory(@PathVariable(value = "id")Integer id){
        categoryService.inactiveProduct(id);
        return ResponseEntity.noContent().build();
    }

}
