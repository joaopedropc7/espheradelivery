package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.Links;

import java.util.List;

public record ProductReportResponseDTO(
        Integer productId,
        String productName,
        Integer quantitySales,
        Double valueSellTotal,
        Links links
) {
    public ProductReportResponseDTO(ProductModel productModel){
        this(
                productModel.getId(),
                productModel.getName(),
                productModel.getSales(),
                productModel.getValueSellTotal(),
                productModel.getLinks()
        );
    }

    public static Page<ProductReportResponseDTO> convert(Page<ProductModel> productModel){
        List<ProductReportResponseDTO> products = productModel
                .stream()
                .map(ProductReportResponseDTO::new)
                .toList();
        return new PageImpl<>(products, productModel.getPageable(), productModel.getTotalElements());
    }
}
