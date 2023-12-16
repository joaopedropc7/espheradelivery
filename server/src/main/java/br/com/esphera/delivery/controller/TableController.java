package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.models.ProductTableModel;
import br.com.esphera.delivery.models.TableModel;
import br.com.esphera.delivery.service.TableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/table")
public class TableController {

    @Autowired
    private TableService tableService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/create/{tableNumber}")
    @Operation(summary = "create a table parsing body and idCompany", description = "create table parsing body and idCompany", tags = {"Table"})
    public ResponseEntity<TableModel> createTable(HttpServletRequest request, @PathVariable(value = "tableNumber") Integer tableNumber){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        return ResponseEntity.ok().body(tableService.inserTableInRest(tableNumber, companyId));
    }

    @GetMapping("/find/{tableId}")
    @Operation(summary = "Get a table parsing id table", description = "create table parsing id table", tags = {"Table"})
    public ResponseEntity<TableModel> getTableById(@PathVariable(value = "tableId") Integer tableId){
        return ResponseEntity.ok().body(tableService.getTableById(tableId));
    }

    @GetMapping("/findtables")
    @Operation(summary = "find all tables parsing idCompany", description = "find all tables parsing idCompany", tags = {"Table"})
    public ResponseEntity<List<TableModel>> getTablesByCompany(HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        List<TableModel> tables = tableService.getAllTablesByCompany(companyId);
        return ResponseEntity.ok().body(tables);
    }

    @PostMapping("/addproduct/{tableId}/{productId}/{productQuantity}")
    @Operation(summary = "add product in table parsing id table, id product and quantity", description = "add product in table parsing id table, id product and quantity", tags = {"Table"})
    public ResponseEntity<ProductTableModel> addProductInTable(@PathVariable(value = "tableId") Integer tableId, @PathVariable(value = "productId") Integer productId, @PathVariable(value = "productQuantity") Integer productQuantity, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        return ResponseEntity.ok().body(tableService.insertProductInTable(tableId, productId, productQuantity, companyId));
    }

    @PutMapping("/alterquantityproduct/{tableId}/{productId}/{productQuantity}")
    @Operation(summary = "alter product in table parsing id table, id product and quantity", description = "alter product in table parsing id table, id product and quantity", tags = {"Table"})
    public ResponseEntity<ProductTableModel> alterQuantityProductInTable(HttpServletRequest request,@PathVariable(value = "tableId") Integer tableId, @PathVariable(value = "productId") Integer productId, @PathVariable(value = "productQuantity") Integer productQuantity){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        return ResponseEntity.ok().body(tableService.alterQuantityProductInTable(tableId, productId, productQuantity));
    }

    @PutMapping("/removeproduct/{tableId}/{productId}")
    @Operation(summary = "remove product in table parsing id table, id product", description = "remove product in table parsing id table, id product", tags = {"Table"})
    public ResponseEntity<String> removeProductFromTable(HttpServletRequest request ,@PathVariable(value = "tableId") Integer tableId, @PathVariable(value = "productId") Integer productId){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        tableService.removeProductFromTable(tableId, productId);
        return ResponseEntity.ok().body("Produto removido da mesa!");
    }

    @GetMapping("/quantityproducts/{tableId}")
    @Operation(summary = "get quantity products in table", description = "get quantity products in table", tags = {"Table"})
    public ResponseEntity<Integer> getQuantityProductsInTable(HttpServletRequest request ,@PathVariable(value = "tableId") Integer tableId){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        return ResponseEntity.ok().body(tableService.getQuantityProductsInTable(tableId));
    }

    @GetMapping("/amounttable/{tableId}")
    @Operation(summary = "get value commands table", description = "get value commands table", tags = {"Table"})
    public ResponseEntity<Double> getAmountTable(HttpServletRequest request,@PathVariable(value = "tableId") Integer tableId){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        return ResponseEntity.ok().body(tableService.getCommandValueTable(tableId));
    }

    @PutMapping("/altertable/{tableId}/{tableNumber}")
    @Operation(summary = "alter number table", description = "alter number table", tags = {"Table"})

    public ResponseEntity<TableModel> alterTable(HttpServletRequest request ,@PathVariable(value = "tableId") Integer tableId, @PathVariable(value = "tableNumber") Integer tableNumber){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        return ResponseEntity.ok().body(tableService.putTable(tableId, tableNumber));
    }

    @PutMapping("/inactivetable/{tableId}")
    @Operation(summary = "inactive table by id", description = "inactive table by id", tags = {"Table"})
    public ResponseEntity<TableModel> inactivateTable(HttpServletRequest request,@PathVariable(value = "tableId") Integer tableId){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        tableService.inactiveTable(tableId);
        return ResponseEntity.noContent().build();
    }




}
