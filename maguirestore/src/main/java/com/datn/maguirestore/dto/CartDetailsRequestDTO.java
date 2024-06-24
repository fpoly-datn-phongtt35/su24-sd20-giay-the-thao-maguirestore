package com.datn.maguirestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailsRequestDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private Integer status;

    private Long cart;

    private Long shoesDetails;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;
}
