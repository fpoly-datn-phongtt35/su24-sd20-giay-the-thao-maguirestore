package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.ShoesDetailDTOCustom;
import com.datn.maguirestore.service.ShoesDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shoes-detail-view")
@RequiredArgsConstructor
public class ShoesDetailViewClientController {

    private static final String ENTITY_NAME = "shoes-details";
    private final Logger log = LoggerFactory.getLogger(ShoesDetailsController.class);
    private final ShoesDetailsService shoesDetailsService;

}
