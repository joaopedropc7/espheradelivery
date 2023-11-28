package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.CouponModel;
import br.com.esphera.delivery.models.DTOS.CreateCouponDTO;
import br.com.esphera.delivery.service.CouponService;
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
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

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
    public CouponModel findCouponById(@PathVariable(value = "couponId")Integer couponId){
        return couponService.getCouponById(couponId);
    }

    @PostMapping("/{companyId}")
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
    public CouponModel createCoupon(@PathVariable(value = "companyId")Integer companyId, @RequestBody CreateCouponDTO createCouponDTO){
        return couponService.createdCoupon(createCouponDTO, companyId);
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
    public ResponseEntity inactiveCoupon(@PathVariable(value = "couponId") Integer couponId){
        couponService.inactiveCoupon(couponId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find/{companyId}")
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
    public ResponseEntity<List<CouponModel>> findCouponsByCompany(@PathVariable(value = "companyId")Integer companyId){
        List<CouponModel> coupons = couponService.findCouponsByCompany(companyId);
        return ResponseEntity.ok().body(coupons);
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
    public ResponseEntity<Double> getDiscountByCouponAndCompany(@PathVariable(value = "couponName")String couponName,@PathVariable(value = "companyId")Integer companyId){
        Double valuePercent = couponService.getDiscountByCoupon(couponName, companyId);
        return ResponseEntity.ok().body(valuePercent);
    }

}
