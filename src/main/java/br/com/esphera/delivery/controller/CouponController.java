package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.CouponModel;
import br.com.esphera.delivery.models.DTOS.CreateCouponDTO;
import br.com.esphera.delivery.service.CouponService;
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
    public CouponModel findCouponById(@PathVariable(value = "couponId")Integer couponId){
        return couponService.getCouponById(couponId);
    }

    @PostMapping("/{companyId}")
    public CouponModel createCoupon(@PathVariable(value = "companyId")Integer companyId, @RequestBody CreateCouponDTO createCouponDTO){
        return couponService.createdCoupon(createCouponDTO, companyId);
    }

    @PutMapping("/{couponId}")
    public ResponseEntity inactiveCoupon(@PathVariable(value = "couponId") Integer couponId){
        couponService.inactiveCoupon(couponId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find/{companyId}")
    public ResponseEntity<List<CouponModel>> findCouponsByCompany(@PathVariable(value = "companyId")Integer companyId){
        List<CouponModel> coupons = couponService.findCouponsByCompany(companyId);
        return ResponseEntity.ok().body(coupons);
    }

    @GetMapping("/{couponName}/{companyId}")
    public ResponseEntity<Double> getDiscountByCouponAndCompany(@PathVariable(value = "couponName")String couponName,@PathVariable(value = "companyId")Integer companyId){
        Double valuePercent = couponService.getDiscountByCoupon(couponName, companyId);
        return ResponseEntity.ok().body(valuePercent);
    }

}
