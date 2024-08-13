package com.datn.maguirestore.repository.custom;

import com.datn.maguirestore.dto.OrderSearchReqDTO;
import com.datn.maguirestore.dto.OrderSearchResDTO;
import com.datn.maguirestore.dto.OrderStatusDTO;
import com.datn.maguirestore.dto.RevenueDTO;
import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderSearchResDTO> search(OrderSearchReqDTO orderSearchReqDTO);
    List<OrderStatusDTO> getQuantityOrders();
    List<RevenueDTO> getRevenueInYear(Integer on);
}
