package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.ShoesDetailsDTO;
import com.datn.maguirestore.repository.OrderRepository;
import com.datn.maguirestore.repository.ShoesDetailsRepository;
import com.datn.maguirestore.service.mapper.ShoesDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DashBoardService {

    private final OrderRepository orderRepository;
    private final ShoesDetailsRepository shoesDetailsRepository;
    private final ShoesDetailsMapper shoesDetailsMapper;

    public Integer getOrderNumberInWeek() {
        return orderRepository.getOrderNumberInWeek();
    }

    public Integer getTotalCustomer() {
        return orderRepository.getTotalCustomer();
    }

    public BigDecimal getRevenueOnline() {
        return orderRepository.getRevenueOnline();
    }

    public BigDecimal getRevenueOnShop() {
        return orderRepository.getRevenueOnShop();
    }

    public List<ShoesDetailsDTO> getAllBestSelling() {
        return shoesDetailsMapper.toDto(shoesDetailsRepository.getTopBestSelling());
    }
}
