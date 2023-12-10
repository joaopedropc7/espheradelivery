package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.Links;

import java.util.List;

public record CategoryResponseDTO(
        Integer id,
        String categoryName,
        Boolean inactive,
        Integer companyId,
        Links links
) {

    public CategoryResponseDTO(CategoryModel categoryModel) {
        this(
                categoryModel.getId(),
                categoryModel.getCategoryName(),
                categoryModel.getInactive(),
                categoryModel.getCompanyModel().getId(),
                categoryModel.getLinks()
        );
    }

    public static Page<CategoryResponseDTO> convert(Page<CategoryModel> categorys) {
        List<CategoryResponseDTO> categoryDTOs = categorys
                .stream()
                .map(CategoryResponseDTO::new)
                .toList();
        return new PageImpl<>(categoryDTOs, categorys.getPageable(), categorys.getTotalElements());
    }
}
