package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CategoryModel;
import br.com.esphera.delivery.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryModel createCategory(String nameCategory){
        CategoryModel categoryModel = new CategoryModel(nameCategory);
        categoryRepository.save(categoryModel);
        return categoryModel;
    }

    public List<CategoryModel> findAllCategorys(){
        List<CategoryModel> categorys = categoryRepository.findAll();
        return categorys;
    }

    public CategoryModel findById(Integer id){
        CategoryModel category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return category;
    }

    public CategoryModel updateCategory(Integer id, String nameCategory){
        CategoryModel category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        category.setCategoryName(nameCategory);
        categoryRepository.save(category);
        return category;
    }

    public void inactiveProduct(Integer id){
        CategoryModel categoryModel = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        categoryModel.setInactive(true);
        categoryRepository.save(categoryModel);
    }

}
