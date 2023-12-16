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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Operation(summary = "Get a coupon by Id", description = "Get a coupon by Id", tags = {"Coupon"})
    public ResponseEntity<CouponResponseDTO> findCouponById(@PathVariable(value = "couponId")Integer couponId){
        CouponModel couponModel = couponService.getCouponById(couponId);
        CouponResponseDTO couponResponseDTO = new CouponResponseDTO(couponModel);
        return ResponseEntity.ok().body(couponResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a coupon by Id company", description = "Create a coupon by id company", tags = {"Coupon"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CouponResponseDTO> createCoupon(HttpServletRequest request, @RequestBody CreateCouponDTO createCouponDTO){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        CouponModel couponModel = couponService.createdCoupon(createCouponDTO, companyId);
        CouponResponseDTO couponResponseDTO = new CouponResponseDTO(couponModel);
        return ResponseEntity.ok().body(couponResponseDTO);
    }

    @PutMapping("/{couponId}")
    @Operation(summary = "Put a coupon by Id", description = "Put a coupon by Id", tags = {"Coupon"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity inactiveCoupon(@PathVariable(value = "couponId") Integer couponId, HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        couponService.inactiveCoupon(couponId, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find")
    @Operation(summary = "Find all coupons by company", description = "Find all coupons by company", tags = {"Coupon"})
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<CouponResponseDTO>> findCouponsByCompany(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "12") Integer limit, @RequestParam(value = "direction", defaultValue = "asc") String direction){
        String token = tokenService.recoverToken(request);
        Integer companyId = tokenService.getCompanyIdFromToken(token);
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));
        Page<CouponModel> coupons = couponService.findCouponsByCompany(companyId, pageable);
        Page<CouponResponseDTO> couponResponseDTOS = CouponResponseDTO.convert(coupons);
        return ResponseEntity.ok().body(couponResponseDTOS);
    }

    @GetMapping("/{couponName}/{companyId}")
    @Operation(summary = "Find a coupon by name and company id", description = "Find a coupon by name and company id", tags = {"Coupon"})
    public ResponseEntity<Double> getDiscountByCouponAndCompany(@PathVariable(value = "couponName")String couponName, @PathVariable(value = "companyId")Integer companyId){
        Double valuePercent = couponService.getDiscountByCoupon(couponName, companyId);
        return ResponseEntity.ok().body(valuePercent);
    }


}
