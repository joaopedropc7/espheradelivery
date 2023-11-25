package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.OrderModel;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.service.OrderService;
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
@RequestMapping("/api/sell")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{idCompany}")
    @Operation(summary = "Create Order", description = "Create Order parsing company Id",
            tags = {"Order"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<OrderModel> createSell(@RequestBody OrderCreateRecord data, @PathVariable(value = "idCompany") Integer idCompany){
        OrderModel sellCrated = orderService.createSell(data, idCompany);
        return ResponseEntity.ok(sellCrated);
    }

    @GetMapping("/findall/{companyId}")
    @Operation(summary = "Get all Orders", description = "Get all Orders parsing company Id",
            tags = {"Order"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<OrderModel>> findAllSells(@PathVariable(value = "companyId")Integer companyId){
        List<OrderModel> sells = orderService.findAllSells(companyId);
        return ResponseEntity.ok(sells);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Order by Id", description = "Get order by id",
            tags = {"Order"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<OrderModel> findSellById(@PathVariable(value = "id")Integer id){
        OrderModel orderModel = orderService.findByIdSell(id);
        return ResponseEntity.ok(orderModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel Order", description = "Cancel Order",
            tags = {"Order"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity cancelSell(@PathVariable(value = "id") Integer id){
        orderService.cancelSell(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{statusOrder}/{companyId}")
    @Operation(summary = "Find orders by status", description = "Find orders by status",
            tags = {"Order"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<OrderModel>> findByStatusOrder(@PathVariable(value = "statusOrder") StatusOrder statusOrder, @PathVariable(value = "companyId")Integer companyId){
        List<OrderModel> sells = orderService.findByStatusOrder(statusOrder, companyId);
        return ResponseEntity.ok(sells);
    }

    @PostMapping("/prepared/{idOrder}")
    @Operation(summary = "Parse orders for prepared", description = "Parse orders for prepared",
            tags = {"Order"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity setOrderPrepared(@PathVariable(value = "idOrder") Integer idOrder){
        orderService.setOrderPrepared(idOrder);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ready/{idOrder}")
    @Operation(summary = "Parse order ready for collect", description = "Parse orders ready - Ready for take order (COLLECT, NOT DELIVERY)",
            tags = {"Order"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity setOrderReadyForCollect(@PathVariable(value = "idOrder") Integer idOrder){
        orderService.setOrderReadyForCollect(idOrder);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/inroute/{idOrder}/{motoboyId}")
    @Operation(summary = "Parse orders for in route (DELIVERY ONLY)", description = "Parse orders for in route (DELIVERY ONLY)",
            tags = {"Order"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity setOrderInRoute(@PathVariable(value = "idOrder") Integer idOrder, @PathVariable(value = "motoboyId") Integer motoboyId){
        orderService.setOrderDelivery(idOrder, motoboyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/finished/{idOrder}")
    @Operation(summary = "Parse orders for finished", description = "Parse orders for finished",
            tags = {"Order"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = OrderModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity setOrderDelivered(@PathVariable(value = "idOrder") Integer idOrder){
        orderService.setOrderFinished(idOrder);
        return ResponseEntity.ok().build();
    }

    
}
