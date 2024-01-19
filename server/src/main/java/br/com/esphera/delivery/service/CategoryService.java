package br.com.esphera.delivery.service;

import br.com.esphera.delivery.controller.CategoryController;
import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CategoryModel;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.CategoryRecord;
import br.com.esphera.delivery.models.DTOS.responseDtos.CategoryResponseDTO;
import br.com.esphera.delivery.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CompanyService companyService;

    public CategoryModel createCategory(CategoryRecord categoryRecord, Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        CategoryModel categoryModel = new CategoryModel(categoryRecord, companyModel);
        categoryRepository.save(categoryModel);
        categoryModel.add(linkTo(methodOn(CategoryController.class).findById(categoryModel.getId())).withSelfRel());
        System.out.println(categoryModel);
        return categoryModel;
    }

    public Page<CategoryModel> findCategorysByName(String nameCategory, Integer idCompany, Pageable pageable){
        Page<CategoryModel> categorys = categoryRepository.findCategoryModelByCategoryName(nameCategory, pageable, companyService.getCompanyById(idCompany));
        categorys.stream().forEach(category -> category.add(linkTo(methodOn(CategoryController.class).findById(category.getId())).withSelfRel()));
        return categorys;
    }

    public Page<CategoryModel> findAllCategorys(Integer idCompany, Pageable pageable){
        Page<CategoryModel> categorys = categoryRepository.findCategoryModelByCompanyModel(companyService.getCompanyById(idCompany), pageable);
        categorys.stream().forEach(category -> category.add(linkTo(methodOn(CategoryController.class).findById(category.getId())).withSelfRel()));
        return categorys;
    }

    public CategoryModel findById(Integer id){
        CategoryModel category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        category.add(linkTo(methodOn(CategoryController.class).findById(id)).withSelfRel());
        return category;
    }

    public CategoryModel updateCategory(Integer id, String nameCategory, Integer companyId){
        CategoryModel category = findById(id);
        verifyCategoryBelongsCompany(category, companyId);
        category.setCategoryName(nameCategory);
        categoryRepository.save(category);
        category.add(linkTo(methodOn(CategoryController.class).findById(id)).withSelfRel());
        return category;
    }

    public void inactiveProduct(Integer id, Integer companyId){
        CategoryModel categoryModel = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        verifyCategoryBelongsCompany(categoryModel, companyId);
        categoryModel.setInactive(true);
        categoryRepository.save(categoryModel);
    }

    public void verifyCategoryBelongsCompany(CategoryModel categoryModel, Integer companyId){
        if(!categoryModel.getCompanyModel().getId().equals(companyId)){
            throw new ResourceNotFoundException("Categoria não pertence a empresa!");
        }
    }
}
