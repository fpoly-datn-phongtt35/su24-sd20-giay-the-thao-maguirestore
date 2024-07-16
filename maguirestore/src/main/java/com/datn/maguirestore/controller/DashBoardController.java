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

}
