package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.DTOS.ExpenseDTO;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "expenses")
public class ExpensesModel extends RepresentationModel<ExpensesModel>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nameExpense;
    private LocalDate paymentDate;
    private LocalDate dateLastPayment;
    private Boolean recurringPayment;
    private Double valuePayment;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyModel companyModel;
    private Integer amountPayments;
    private Boolean inactive;

    public ExpensesModel() {
    }

    public ExpensesModel(ExpenseDTO expenseDTO, CompanyModel companyModel){
        this.nameExpense = expenseDTO.nameExpense();
        this.paymentDate = LocalDate.parse(expenseDTO.datePayment());
        this.companyModel = companyModel;
        this.valuePayment = expenseDTO.valuePayment();
        this.inactive = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameExpense() {
        return nameExpense;
    }

    public void setNameExpense(String nameExpense) {
        this.nameExpense = nameExpense;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getDateLastPayment() {
        return dateLastPayment;
    }

    public void setDateLastPayment(LocalDate dateLastPayment) {
        this.dateLastPayment = dateLastPayment;
    }

    public Boolean getRecurringPayment() {
        return recurringPayment;
    }

    public void setRecurringPayment(Boolean recurringPayment) {
        this.recurringPayment = recurringPayment;
    }

    public Double getValuePayment() {
        return valuePayment;
    }

    public void setValuePayment(Double valuePayment) {
        this.valuePayment = valuePayment;
    }

    public CompanyModel getCompanyModel() {
        return companyModel;
    }

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }

    public Integer getAmountPayments() {
        return amountPayments;
    }

    public void setAmountPayments(Integer amountPayments) {
        this.amountPayments = amountPayments;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpensesModel that = (ExpensesModel) o;
        return Objects.equals(id, that.id) && Objects.equals(nameExpense, that.nameExpense) && Objects.equals(paymentDate, that.paymentDate) && Objects.equals(dateLastPayment, that.dateLastPayment) && Objects.equals(recurringPayment, that.recurringPayment) && Objects.equals(valuePayment, that.valuePayment) && Objects.equals(companyModel, that.companyModel) && Objects.equals(amountPayments, that.amountPayments) && Objects.equals(inactive, that.inactive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameExpense, paymentDate, dateLastPayment, recurringPayment, valuePayment, companyModel, amountPayments, inactive);
    }
}
