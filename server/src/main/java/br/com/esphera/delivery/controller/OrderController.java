package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.DTOS.OrderCreateRecord;
import br.com.esphera.delivery.models.DTOS.responseDtos.OrderResponseDTO;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.OrderModel;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sell")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TokenService tokenService;

    @PostMapping
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
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<OrderResponseDTO> createSell(@RequestBody OrderCreateRecord data, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer idCompany = tokenService.getCompanyIdFromToken(token);
        OrderModel sellCrated = orderService.createSell(data, idCompany);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(sellCrated);
        return ResponseEntity.ok().body(orderResponseDTO);
    }

    @GetMapping("/findall")
    @Operation(summary = "Get all Orders", description = "Get all Orders parsing company Id", tags = {"Order"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<OrderResponseDTO>> findAllSells(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        Integer companyId = tokenService.getCompanyIdFromToken(tokenService.recoverToken(request));
        var sortDirection = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "orderDate").descending());
        Page<OrderModel> sells = orderService.findAllSells(companyId, pageable);
        Page<OrderResponseDTO> orderResponseDTOS = OrderResponseDTO.convert(sells);
        return ResponseEntity.ok().body(orderResponseDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Order by Id", description = "Get order by id", tags = {"Order"})
    public ResponseEntity<OrderResponseDTO> findSellById(@PathVariable(value = "id")Integer id){
        OrderModel orderModel = orderService.findByIdSell(id);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(orderModel);
        return ResponseEntity.ok().body(orderResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel Order", description = "Cancel Order", tags = {"Order"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity cancelSell(@PathVariable(value = "id") Integer id, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        orderService.cancelSell(id, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{statusOrder}")
    @Operation(summary = "Find orders by status", description = "Find orders by status", tags = {"Order"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<OrderResponseDTO>> findByStatusOrder(@PathVariable(value = "statusOrder") StatusOrder statusOrder, HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        var sortDirection = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "orderDate"));
        Page<OrderModel> sells = orderService.findByStatusOrder(statusOrder, companyId, pageable);
        Page<OrderResponseDTO> orderResponseDTOS = OrderResponseDTO.convert(sells);
        return ResponseEntity.ok().body(orderResponseDTOS);
    }

    @PostMapping("/prepared/{idOrder}")
    @Operation(summary = "Parse orders for prepared", description = "Parse orders for prepared", tags = {"Order"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity setOrderPrepared(@PathVariable(value = "idOrder") Integer idOrder, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        orderService.setOrderPrepared(idOrder, companyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ready/{idOrder}")
    @Operation(summary = "Parse order ready for collect", description = "Parse orders ready - Ready for take order (COLLECT, NOT DELIVERY)", tags = {"Order"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity setOrderReadyForCollect(@PathVariable(value = "idOrder") Integer idOrder, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        orderService.setOrderReadyForCollect(idOrder, companyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/inroute/{idOrder}/{motoboyId}")
    @Operation(summary = "Parse orders for in route (DELIVERY ONLY)", description = "Parse orders for in route (DELIVERY ONLY)", tags = {"Order"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity setOrderInRoute(@PathVariable(value = "idOrder") Integer idOrder, @PathVariable(value = "motoboyId") Integer motoboyId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        orderService.setOrderDelivery(idOrder, motoboyId, companyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/finished/{idOrder}")
    @Operation(summary = "Parse orders for finished", description = "Parse orders for finished", tags = {"Order"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity setOrderDelivered(@PathVariable(value = "idOrder") Integer idOrder, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        orderService.setOrderFinished(idOrder, companyId);
        return ResponseEntity.noContent().build();
    }

    
}
