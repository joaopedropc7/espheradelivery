package br.com.esphera.delivery.models;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.Enums.StatusTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "restaurant_table")
public class TableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private CompanyModel companyModel;
    private Integer tableNumber;
    @OneToMany(mappedBy = "tableModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductTableModel> productsTable;
    private Double commandsValue;
    private Integer discountPercent;
    private Integer quantityTotalItems;
    private StatusTable statusTable;
    private Boolean tableInactive;

    public TableModel() {
    }

    public TableModel(CompanyModel companyModel, Integer numberTable){
        this.companyModel = companyModel;
        this.tableNumber = numberTable;
        this.statusTable = StatusTable.DISPONIVEL;
        this.commandsValue = 0.0;
        this.discountPercent = 0;
        this.quantityTotalItems = 0;
        this.tableInactive = false;
    }

    public ProductTableModel insertIntoTable(ProductTableModel productTableModel){
            if(productsTable == null){
                productsTable = new ArrayList<>();
            }
            productsTable.add(productTableModel);
            return productTableModel;
    }

    public void removeFromTable(Integer productId){
       ProductTableModel productTableModel =
               productsTable.stream().filter(p -> p.getProduct().getId() == productId).findFirst().orElse(null);
       if (productTableModel != null){
           productsTable.remove(productTableModel);
       }else{
           throw new ResourceNotFoundException("Produto não encontrado na mesa!");
       }
    }

    public ProductTableModel alterQuantityProductInTable(Integer productId, Integer quantityAdd){
        ProductTableModel oldProduct =
                productsTable.stream()
                        .filter(s -> s.getProduct().getId() == productId)
                        .findFirst().orElse(null);
        if (oldProduct != null){
            oldProduct.setQuantity(quantityAdd);
            return oldProduct;}
        else {
            throw new ResourceNotFoundException("Product não encontrado na mesa!");
        }
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

    public List<ProductTableModel> getProductTable() {
        return productsTable;
    }

    public void setProductTable(List<ProductTableModel> productTable) {
        this.productsTable = productTable;
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

    public List<ProductTableModel> getProductsTable() {
        return productsTable;
    }

    public void setProductsTable(List<ProductTableModel> productsTable) {
        this.productsTable = productsTable;
    }

    public StatusTable getStatusTable() {
        return statusTable;
    }

    public void setStatusTable(StatusTable statusTable) {
        this.statusTable = statusTable;
    }

    public Boolean getTableInactive() {
        return tableInactive;
    }

    public void setTableInactive(Boolean tableInactive) {
        this.tableInactive = tableInactive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableModel that = (TableModel) o;
        return Objects.equals(id, that.id) && Objects.equals(companyModel, that.companyModel) && Objects.equals(tableNumber, that.tableNumber) && Objects.equals(productsTable, that.productsTable) && Objects.equals(commandsValue, that.commandsValue) && Objects.equals(discountPercent, that.discountPercent) && Objects.equals(quantityTotalItems, that.quantityTotalItems) && statusTable == that.statusTable && Objects.equals(tableInactive, that.tableInactive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyModel, tableNumber, productsTable, commandsValue, discountPercent, quantityTotalItems, statusTable, tableInactive);
    }
}
