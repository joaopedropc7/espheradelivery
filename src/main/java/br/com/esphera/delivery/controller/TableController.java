package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.models.ProductTableModel;
import br.com.esphera.delivery.models.TableModel;
import br.com.esphera.delivery.service.TableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/table")
public class TableController {

    @Autowired
    private TableService tableService;

    @PostMapping("/create/{companyId}/{tableNumber}")
    @Operation(summary = "create a table parsing body and idCompany", description = "create table parsing body and idCompany",
            tags = {"Table"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<TableModel> createTable(@PathVariable(value = "companyId") Integer companyId, @PathVariable(value = "tableNumber") Integer tableNumber){
        return ResponseEntity.ok().body(tableService.inserTableInRest(tableNumber, companyId));
    }

    @GetMapping("/find/{tableId}")
    @Operation(summary = "Get a table parsing id table", description = "create table parsing id table",
            tags = {"Table"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<TableModel> getTableById(@PathVariable(value = "tableId") Integer tableId){
        return ResponseEntity.ok().body(tableService.getTableById(tableId));
    }

    @GetMapping("/findtables/{companyId}")
    @Operation(summary = "find all tables parsing idCompany", description = "find all tables parsing idCompany",
            tags = {"Table"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<TableModel>> getTablesByCompany(@PathVariable(value = "companyId") Integer companyId){
        List<TableModel> tables = tableService.getAllTablesByCompany(companyId);
        return ResponseEntity.ok().body(tables);
    }

    @PostMapping("/addproduct/{tableId}/{productId}/{productQuantity}")
    @Operation(summary = "add product in table parsing id table, id product and quantity", description = "add product in table parsing id table, id product and quantity",
            tags = {"Table"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ProductTableModel> addProductInTable(@PathVariable(value = "tableId") Integer tableId, @PathVariable(value = "productId") Integer productId, @PathVariable(value = "productQuantity") Integer productQuantity){
        return ResponseEntity.ok().body(tableService.insertProductInTable(tableId, productId, productQuantity));
    }

    @PutMapping("/alterquantityproduct/{tableId}/{productId}/{productQuantity}")
    @Operation(summary = "alter product in table parsing id table, id product and quantity", description = "alter product in table parsing id table, id product and quantity",
            tags = {"Table"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ProductTableModel> alterQuantityProductInTable(@PathVariable(value = "tableId") Integer tableId, @PathVariable(value = "productId") Integer productId, @PathVariable(value = "productQuantity") Integer productQuantity){
        return ResponseEntity.ok().body(tableService.alterQuantityProductInTable(tableId, productId, productQuantity));
    }

    @PutMapping("/removeproduct/{tableId}/{productId}")
    @Operation(summary = "remove product in table parsing id table, id product", description = "remove product in table parsing id table, id product",
            tags = {"Table"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<String> removeProductFromTable(@PathVariable(value = "tableId") Integer tableId, @PathVariable(value = "productId") Integer productId){
        tableService.removeProductFromTable(tableId, productId);
        return ResponseEntity.ok().body("Produto removido da mesa!");
    }

    @GetMapping("/quantityproducts/{tableId}")
    @Operation(summary = "get quantity products in table", description = "get quantity products in table",
            tags = {"Table"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Integer> getQuantityProductsInTable(@PathVariable(value = "tableId") Integer tableId){
        return ResponseEntity.ok().body(tableService.getQuantityProductsInTable(tableId));
    }

    @GetMapping("/amounttable/{tableId}")
    @Operation(summary = "get value commands table", description = "get value commands table",
            tags = {"Table"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Double> getAmountTable(@PathVariable(value = "tableId") Integer tableId){
        return ResponseEntity.ok().body(tableService.getCommandValueTable(tableId));
    }

    @PutMapping("/altertable/{tableId}/{tableNumber}")
    @Operation(summary = "alter number table", description = "alter number table",
            tags = {"Table"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<TableModel> alterTable(@PathVariable(value = "tableId") Integer tableId, @PathVariable(value = "tableNumber") Integer tableNumber){
        return ResponseEntity.ok().body(tableService.putTable(tableId, tableNumber));
    }

    @PutMapping("/inactivetable/{tableId}")
    @Operation(summary = "inactive table by id", description = "inactive table by id",
            tags = {"Table"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = TableModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<TableModel> inactivateTable(@PathVariable(value = "tableId") Integer tableId){
        tableService.inactiveTable(tableId);
        return ResponseEntity.noContent().build();
    }




}
