package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public record ProductResponseDTO(
        Integer id,
        String name,
        String description,
        Double costValue,
        Double valueSell,
        Integer quantity,
        Integer sales,
        Double valueBuyTotal,
        Double valueSellTotal,
        Boolean inactive,
        Integer categoryId,
        String categoryName,
        Integer companyId,
        List<OptionalResponseDTO> optionals,
        Links links
) {
    public ProductResponseDTO(ProductModel productModel)  {
        this(
                productModel.getId(),
                productModel.getName(),
                productModel.getDescription(),
                productModel.getCostValue(),
                productModel.getValueSell(),
                productModel.getQuantity(),
                productModel.getSales(),
                productModel.getValueBuyTotal(),
                productModel.getValueSellTotal(),
                productModel.getInactive(),
                productModel.getCategoryModel().getId(),
                productModel.getCategoryModel().getCategoryName(),
                productModel.getCompanyModel().getId(),
                OptionalResponseDTO.convertToList(productModel.getOptionals()),
                productModel.getLinks()
        );
    }

    public static Page<ProductResponseDTO> convert(Page<ProductModel> products) {
        List<ProductResponseDTO> productDTOs = products
                .stream()
                .map(ProductResponseDTO::new)
                .toList();
        return new PageImpl<>(productDTOs, products.getPageable(), products.getTotalElements());
    }
}
