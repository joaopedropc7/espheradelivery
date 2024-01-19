package br.com.esphera.delivery.service;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.ExpenseDTO;
import br.com.esphera.delivery.models.DTOS.responseDtos.ExpensesDTOResponse;
import br.com.esphera.delivery.models.ExpensesModel;
import br.com.esphera.delivery.repository.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpensesService {

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private CompanyService companyService;

    /* public ExpensesDTOResponse createExpenseDto(ExpenseDTO expenseDTO){
        CompanyModel companyModel = companyService.getCompanyById(expenseDTO.companyId());
        ExpensesModel expensesModel = new ExpensesModel(expenseDTO, companyModel);

    }*/

}
