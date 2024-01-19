package br.com.esphera.delivery.models.DTOS.responseDtos;

import br.com.esphera.delivery.models.ExpensesModel;
import org.springframework.hateoas.Links;

public record ExpensesDTOResponse(
        Integer id,
        String nameExpense,
        String datePayment,
        String dateLastPayment,
        Integer amountPayments,
        Double valuePayment,
        Boolean paymentRecurrent,
        Integer companyId,
        Boolean inactive,
        Links links
) {
    public ExpensesDTOResponse(ExpensesModel expensesModel) {
        this(
                expensesModel.getId(),
                expensesModel.getNameExpense(),
                expensesModel.getPaymentDate() != null ? expensesModel.getPaymentDate().toString() : null,
                expensesModel.getDateLastPayment() != null ? expensesModel.getDateLastPayment().toString() : null,
                expensesModel.getAmountPayments(),
                expensesModel.getValuePayment(),
                expensesModel.getRecurringPayment(),
                expensesModel.getCompanyModel().getId(),
                expensesModel.getInactive(),
                expensesModel.getLinks()
        );
    }
}
