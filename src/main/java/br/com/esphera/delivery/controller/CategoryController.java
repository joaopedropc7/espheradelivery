package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.CategoryModel;
import br.com.esphera.delivery.models.DTOS.CategoryRecord;
import br.com.esphera.delivery.service.CategoryService;
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
    public CategoryModel createCategory(@RequestBody CategoryRecord nameCategory, @PathVariable(value = "companyId") Integer companyId){
        CategoryModel categoryModel = categoryService.createCategory(nameCategory, companyId);
        return  categoryModel;
    }

    @GetMapping
    public List<CategoryModel> findAllCategorys(){
        List<CategoryModel> categorys = categoryService.findAllCategorys();
        return categorys;
    }

    @GetMapping("/{id}")
    public CategoryModel findById(@PathVariable(value = "id") Integer id){
        CategoryModel categoryModel = categoryService.findById(id);
        return categoryModel;
    }

    @PutMapping("/{id}")
    public CategoryModel updateCategory(@PathVariable(value = "id")Integer categoryId, @RequestBody String categoryName){
        CategoryModel categoryModel = categoryService.updateCategory(categoryId, categoryName);
        return categoryModel;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity inactiveCategory(@PathVariable(value = "id")Integer id){
        categoryService.inactiveProduct(id);
        return ResponseEntity.ok().build();
    }

}
