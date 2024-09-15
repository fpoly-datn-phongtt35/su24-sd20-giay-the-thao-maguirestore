package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.ShoesDetailsDTO;
import com.datn.maguirestore.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
public class DashBoardController {

    private final DashBoardService dashboardService;

    @GetMapping("/order-number")
    public ResponseEntity<Integer> getOrderNumber() {
        return ResponseEntity.ok(dashboardService.getOrderNumberInWeek());
    }

    @GetMapping("/customers")
    public ResponseEntity<Integer> getCustomers() {
        return ResponseEntity.ok(dashboardService.getTotalCustomer());
    }

    @GetMapping("/order-revenue-on")
    public ResponseEntity<BigDecimal> getRevenueOn() {
        return ResponseEntity.ok(dashboardService.getRevenueOnline());
    }

    @GetMapping("/order-revenue-off")
    public ResponseEntity<BigDecimal> getRevenueOff() {
        return ResponseEntity.ok(dashboardService.getRevenueOnShop());
    }

    @GetMapping("/best-selling")
    public ResponseEntity<List<ShoesDetailsDTO>> getBestSelling() {
        return ResponseEntity.ok(dashboardService.getAllBestSelling());
    }
}
