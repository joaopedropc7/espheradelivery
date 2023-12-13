package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.ProductEntryItemModel;
import br.com.esphera.delivery.models.ProductEntryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.Links;

import java.util.List;

public record EntryResponseDTO(
        Integer id,
        Double totalValue,
        String supplier,
        String dateEntry,
        Integer quantityAllProducts,
        Boolean entryCanceled,
        List<ProductEntryItemModel> products,
        Integer companyId,
        Links links
) {
    public EntryResponseDTO(ProductEntryModel entryModel) {
        this(
                entryModel.getId(),
                entryModel.getTotalValue(),
                entryModel.getSupplier(),
                entryModel.getDateEntry().toString(),
                entryModel.getQuantityAllProducts(),
                entryModel.getEntryCanceled(),
                entryModel.getProducts(),
                entryModel.getCompanyModel().getId(),
                entryModel.getLinks()
        );
    }

    public static Page<EntryResponseDTO> convert(Page<ProductEntryModel> entryModels) {
        List<EntryResponseDTO> entryResponseDTOS = entryModels
                .stream()
                .map(EntryResponseDTO::new)
                .toList();
        return new PageImpl<>(entryResponseDTOS, entryModels.getPageable(), entryModels.getTotalElements());
    }
}
