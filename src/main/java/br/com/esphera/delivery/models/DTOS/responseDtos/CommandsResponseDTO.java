package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.CommandsTableModel;
import br.com.esphera.delivery.models.Enums.PaymentsMethod;
import br.com.esphera.delivery.models.ProductTableModel;

import java.time.LocalDateTime;
import java.util.List;

public record CommandsResponseDTO(
        Integer id,
        Integer idLocalCommand,
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
                commandsTableModel.getIdLocalCommand(),
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

    public static List<CommandsResponseDTO> convert(List<CommandsTableModel> commandsTableModels) {
        return commandsTableModels.stream().map(CommandsResponseDTO::new).toList();
    }
}
