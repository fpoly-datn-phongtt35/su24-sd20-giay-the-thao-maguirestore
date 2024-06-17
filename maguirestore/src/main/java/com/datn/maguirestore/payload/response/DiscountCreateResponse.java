package com.datn.maguirestore.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class DiscountCreateResponse {
    private Long id;
    private String code;
    private String name;
    private Integer discountMethod;

    private Integer status;

    private String createdBy;
    private Instant createdDate;
}
