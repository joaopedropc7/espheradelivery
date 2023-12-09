package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.CouponModel;
import br.com.esphera.delivery.models.DTOS.CreateCouponDTO;
import br.com.esphera.delivery.models.DTOS.responseDtos.CouponResponseDTO;
import br.com.esphera.delivery.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/{couponId}")
    @Operation(summary = "Get a coupon by Id", description = "Get a coupon by Id",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CouponModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<CouponResponseDTO> findCouponById(@PathVariable(value = "couponId")Integer couponId){
        CouponModel couponModel = couponService.getCouponById(couponId);
        CouponResponseDTO couponResponseDTO = new CouponResponseDTO(couponModel);
        return ResponseEntity.ok().body(couponResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a coupon by Id company", description = "Create a coupon by id company",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CouponModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CouponResponseDTO> createCoupon(HttpServletRequest request, @RequestBody CreateCouponDTO createCouponDTO){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CouponModel couponModel = couponService.createdCoupon(createCouponDTO, companyId);
        CouponResponseDTO couponResponseDTO = new CouponResponseDTO(couponModel);
        return ResponseEntity.ok().body(couponResponseDTO);
    }

    @PutMapping("/{couponId}")
    @Operation(summary = "Put a coupon by Id", description = "Put a coupon by Id",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CouponModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity inactiveCoupon(@PathVariable(value = "couponId") Integer couponId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        couponService.inactiveCoupon(couponId, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find")
    @Operation(summary = "Find all coupons by company", description = "Find all coupons by company",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CouponModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<CouponResponseDTO>> findCouponsByCompany(HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        List<CouponModel> coupons = couponService.findCouponsByCompany(companyId);
        List<CouponResponseDTO> couponResponseDTOS = CouponResponseDTO.convert(coupons);
        return ResponseEntity.ok().body(couponResponseDTOS);
    }

    @GetMapping("/{couponName}/{companyId}")
    @Operation(summary = "Find a coupon by name and company id", description = "Find a coupon by name and company id",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CouponModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Double> getDiscountByCouponAndCompany(@PathVariable(value = "couponName")String couponName, @PathVariable(value = "companyId")Integer companyId){
        Double valuePercent = couponService.getDiscountByCoupon(couponName, companyId);
        return ResponseEntity.ok().body(valuePercent);
    }

    @GetMapping("/local/{idLocal}")
    @Operation(summary = "Find a coupon by localID", description = "Find a coupon by localID",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CouponModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<CouponResponseDTO> getCouponByLocalId(@PathVariable(value = "idLocal")Integer idLocal,HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CouponModel couponModel = couponService.getCouponByLocalIdAndCompany(idLocal, companyId);
        CouponResponseDTO couponResponseDTO = new CouponResponseDTO(couponModel);
        return ResponseEntity.ok().body(couponResponseDTO);
    }

}
