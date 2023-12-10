package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.CommandsTableModel;
import br.com.esphera.delivery.models.Enums.PaymentsMethod;
import br.com.esphera.delivery.models.ProductTableModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

public record CommandsResponseDTO(
        Integer id,
        Integer idTableNumber,
        List<ProductTableModel> products,
        Double commandValue,
        Integer discountPercent,
        Integer quantityTotalItems,
        PaymentsMethod methodPayment,
        LocalDateTime dateCommand,
        String observation
) {

    public CommandsResponseDTO(CommandsTableModel commandsTableModel) {
        this(
                commandsTableModel.getId(),
                commandsTableModel.getTableNumber(),
                commandsTableModel.getProductsTable(),
                commandsTableModel.getCommandsValue(),
                commandsTableModel.getDiscountPercent(),
                commandsTableModel.getQuantityTotalItems(),
                commandsTableModel.getMethodPayment(),
                commandsTableModel.getDateCommand(),
                commandsTableModel.getObservation()
        );
    }

    public static Page<CommandsResponseDTO> convert(Page<CommandsTableModel> commandsTableModels) {
        List<CommandsResponseDTO> commandsResponseDTOS = commandsTableModels
                .stream()
                .map(CommandsResponseDTO::new)
                .toList();
        return new PageImpl<>(commandsResponseDTOS, commandsTableModels.getPageable(), commandsTableModels.getTotalElements());
    }
}
