package com.datn.maguirestore.dto;

import com.datn.maguirestore.entity.Address;
import com.datn.maguirestore.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResDTO {

    private Long id;

    private String code;

    private String address;

    private String phone;

    private Integer paymentMethod;
    private Integer paymentStatus;

    private BigDecimal shipPrice;
    private String mailAddress;

    private BigDecimal totalPrice;

    private String receivedBy;

    private Instant receivedDate;

    private Instant shippedDate;

    private Integer status;
    private UserDTO owner;
    private Payment payment;
    private Address userAddress;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
    private List<OrderDetailsDTO> orderDetailsDTOList;
}
