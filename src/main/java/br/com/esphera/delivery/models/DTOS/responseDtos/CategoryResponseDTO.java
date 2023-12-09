package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.CategoryModel;

import java.util.List;

public record CategoryResponseDTO(
        Integer id,
        Integer idLocalCategory,
        String categoryName,
        Boolean inactive,
        Integer companyId
) {

    public CategoryResponseDTO(CategoryModel categoryModel) {
        this(
                categoryModel.getId(),
                categoryModel.getIdLocalCategory(),
                categoryModel.getCategoryName(),
                categoryModel.getInactive(),
                categoryModel.getCompanyModel().getId()
        );
    }

    public static List<CategoryResponseDTO> convert(List<CategoryModel> categorys) {
        return categorys.stream().map(CategoryResponseDTO::new).toList();
    }
}
