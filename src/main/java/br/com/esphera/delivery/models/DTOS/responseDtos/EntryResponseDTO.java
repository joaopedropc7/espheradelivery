package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.ProductEntryItemModel;
import br.com.esphera.delivery.models.ProductEntryModel;

import java.util.List;

public record EntryResponseDTO(
        Integer id,
        Integer idLocalEntry,
        Double totalValue,
        String supplier,
        String dateEntry,
        Integer quantityAllProducts,
        Boolean entryCanceled,
        List<ProductEntryItemModel> products,
        Integer companyId
) {
    public EntryResponseDTO(ProductEntryModel entryModel) {
        this(
                entryModel.getId(),
                entryModel.getIdLocalEntry(),
                entryModel.getTotalValue(),
                entryModel.getSupplier(),
                entryModel.getDateEntry().toString(),
                entryModel.getQuantityAllProducts(),
                entryModel.getEntryCanceled(),
                entryModel.getProducts(),
                entryModel.getCompanyModel().getId()
        );
    }

    public static List<EntryResponseDTO> convert(List<ProductEntryModel> entryModels) {
        return entryModels.stream().map(EntryResponseDTO::new).toList();
    }
}
