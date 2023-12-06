package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.ProductModel;
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
        Integer companyId
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
                productModel.getCompanyModel().getId()
        );
    }

    public static List<ProductResponseDTO> convert(List<ProductModel> products) {
        return products.stream().map(ProductResponseDTO::new).toList();
    }
}
