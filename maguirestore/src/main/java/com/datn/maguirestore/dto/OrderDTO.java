package com.datn.maguirestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {

    private Long id;

    private String code;

    private String address;

    private String phone;

    private Integer paymentMethod;

    private BigDecimal shipPrice;

    private BigDecimal totalPrice;

    private String receivedBy;

    private Instant receivedDate;

    private Instant shippedDate;

    private Integer status;

    private String createdBy;

    private String mailAddress;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private UserDTO owner;
    private AddressDTO userAddress;

    public OrderDTO(
            String code,
            String address,
            String phone,
            Integer paymentMethod,
            BigDecimal shipPrice,
            BigDecimal totalPrice,
            String receivedBy,
            Instant receivedDate,
            Instant shippedDate,
            Integer status,
            String createdBy,
            Instant createdDate,
            String lastModifiedBy,
            Instant lastModifiedDate,
            UserDTO owner,
            AddressDTO userAddress
    ) {
        this.code = code;
        this.address = address;
        this.phone = phone;
        this.paymentMethod = paymentMethod;
        this.shipPrice = shipPrice;
        this.totalPrice = totalPrice;
        this.receivedBy = receivedBy;
        this.receivedDate = receivedDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.owner = owner;
        this.userAddress = userAddress;
    }
}
