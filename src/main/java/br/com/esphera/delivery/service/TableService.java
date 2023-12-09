package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.Enums.StatusTable;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.models.ProductTableModel;
import br.com.esphera.delivery.models.TableModel;
import br.com.esphera.delivery.repository.TableRepository;
import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProductService productService;

    public TableModel inserTableInRest(Integer tableNumber, Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        Integer quantityTables = quantityTablesByCompany(companyId);
        if (quantityTables >= 25){
            throw new ResourceNotFoundException("Limite de mesas atingido!");
        }
        if (tableRepository.findTableModelByCompanyModelAndTableNumber(companyModel, tableNumber).isPresent()){
            throw new ResourceNotFoundException("Mesa já cadastrada!");
        }
        TableModel tableModel = new TableModel(companyModel, tableNumber);
        tableRepository.save(tableModel);
        return tableModel;
    }

    public ProductTableModel insertProductInTable(Integer tableId, Integer productId, Integer productQuantity, Integer companyId){
        TableModel tableModel = getTableById(tableId);
        verifyTableStatus(tableModel);
        ProductModel productModel = productService.findById(productId);
        ProductTableModel productTableModel = new ProductTableModel(productModel, productQuantity);
        productTableModel.setTableModel(tableModel);
        ProductTableModel productInTable = tableModel.insertIntoTable(productTableModel);
        tableRepository.save(tableModel);
        calculeAmountTable(tableModel);
        return productInTable;
    }

    public ProductTableModel alterQuantityProductInTable(Integer tableId, Integer productId, Integer productQuantity){
        TableModel tableModel = getTableById(tableId);
        ProductTableModel productTableModel = tableModel.alterQuantityProductInTable(productId, productQuantity);
        tableRepository.save(tableModel);
        calculeAmountTable(tableModel);
        return productTableModel;
    }

    public void removeProductFromTable(Integer tableId, Integer productId){
        TableModel tableModel = getTableById(tableId);
        tableModel.removeFromTable(productId);
        tableRepository.save(tableModel);
        calculeAmountTable(tableModel);
    }

    public Integer getQuantityProductsInTable(Integer tableId){
        TableModel tableModel = getTableById(tableId);
        Integer productsInCart = 0;
        if (tableModel.getProductsTable() != null){
            for(ProductTableModel product : tableModel.getProductsTable()){
                productsInCart += product.getQuantity();
            }
        }
        return productsInCart;
    }

    public Double getCommandValueTable(Integer tableId){
        TableModel tableModel = getTableById(tableId);
        return tableModel.getCommandsValue();
    }

    public void calculeAmountTable(TableModel tableModel){
        tableModel.setCommandsValue(0.0);
        for(ProductTableModel tableProducts : tableModel.getProductsTable()){
            tableModel.setCommandsValue(tableModel.getCommandsValue() + tableProducts.getProduct().getValueSell());
        }
        tableRepository.save(tableModel);
    }

    public TableModel getTableById(Integer tableId){
        return tableRepository.findById(tableId).orElseThrow(() -> new ResourceClosedException("Mesa não encontrada!"));
    }

    public List<TableModel> getAllTablesByCompany(Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        return tableRepository.findAllByCompanyModel(companyModel);
    }

    public TableModel putTable(Integer tableId, Integer tableNumber){
        TableModel tableModel = getTableById(tableId);
        if (tableRepository.findTableModelByCompanyModelAndTableNumber(tableModel.getCompanyModel(), tableNumber).isPresent()){
            throw new ResourceNotFoundException("Já existe uma mesa com esse número!");
        }
        tableModel.setTableNumber(tableNumber);
        tableRepository.save(tableModel);
        return tableModel;
    }

    public void clearTable(Integer tableId){
        TableModel tableModel = getTableById(tableId);
        tableModel.setProductsTable(null);
        tableModel.setCommandsValue(0.0);
        tableModel.setStatusTable(StatusTable.DISPONIVEL);
        tableRepository.save(tableModel);
    }

    public void setTableOcupada(Integer tableId){
        TableModel tableModel = getTableById(tableId);
        tableModel.setStatusTable(StatusTable.OCUPADA);
        tableRepository.save(tableModel);
    }

    public void setTableReservada(Integer tableId){
        TableModel tableModel = getTableById(tableId);
        tableModel.setStatusTable(StatusTable.RESERVADA);
        tableRepository.save(tableModel);
    }

    public void inactiveTable(Integer tableId){
        TableModel tableModel = getTableById(tableId);
        tableModel.setTableInactive(true);
        tableRepository.save(tableModel);
    }

    public void deleteTable(Integer tableId){
        TableModel tableModel = getTableById(tableId);
        tableRepository.delete(tableModel);
    }

    public Integer quantityTablesByCompany(Integer companyId){
        CompanyModel companyModel = companyService.getCompanyById(companyId);
        return tableRepository.countTableModelByCompanyModel(companyModel);
    }

    public void verifyTableStatus(TableModel tableModel){
        if (tableModel.getStatusTable() == StatusTable.OCUPADA){
            throw new ResourceNotFoundException("Mesa ocupada!");
        }
        if (tableModel.getStatusTable() == StatusTable.RESERVADA){
            throw new ResourceNotFoundException("Mesa reservada!");
        }
        if (tableModel.getTableInactive()){
            throw new ResourceNotFoundException("Mesa inativa!");
        }
    }

}
