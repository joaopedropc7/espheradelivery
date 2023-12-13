package br.com.esphera.delivery.models;

import br.com.esphera.delivery.models.Enums.PaymentsMethod;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "commands_table")
public class CommandsTableModel extends RepresentationModel<CommandsTableModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "company_id")
    private CompanyModel companyModel;
    private Integer tableNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductTableModel> productsTable;
    private Double commandsValue;
    private Integer discountPercent;
    private Integer quantityTotalItems;
    private PaymentsMethod methodPayment;
    private LocalDateTime dateCommand;
    private String observation;


    public CommandsTableModel() {
    }

    public CommandsTableModel (CompanyModel companyModel, TableModel tableModel, PaymentsMethod paymentMethod, String observation){
        this.companyModel = companyModel;
        this.tableNumber = tableModel.getTableNumber();
        this.productsTable = tableModel.getProductsTable();
        this.commandsValue = tableModel.getCommandsValue();
        this.discountPercent = tableModel.getDiscountPercent();
        this.quantityTotalItems = tableModel.getQuantityTotalItems();
        this.methodPayment = paymentMethod;
        this.dateCommand = LocalDateTime.now();
        this.observation = observation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CompanyModel getCompanyModel() {
        return companyModel;
    }

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public List<ProductTableModel> getProductsTable() {
        return productsTable;
    }

    public void setProductsTable(List<ProductTableModel> productsTable) {
        this.productsTable = productsTable;
    }

    public Double getCommandsValue() {
        return commandsValue;
    }

    public void setCommandsValue(Double commandsValue) {
        this.commandsValue = commandsValue;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Integer getQuantityTotalItems() {
        return quantityTotalItems;
    }

    public void setQuantityTotalItems(Integer quantityTotalItems) {
        this.quantityTotalItems = quantityTotalItems;
    }

    public PaymentsMethod getMethodPayment() {
        return methodPayment;
    }

    public void setMethodPayment(PaymentsMethod methodPayment) {
        this.methodPayment = methodPayment;
    }

    public LocalDateTime getDateCommand() {
        return dateCommand;
    }

    public void setDateCommand(LocalDateTime dateCommand) {
        this.dateCommand = dateCommand;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

}
