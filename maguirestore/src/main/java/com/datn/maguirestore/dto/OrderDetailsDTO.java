package com.datn.maguirestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal price;

    private BigDecimal discount;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private OrderDTO order;

    @NotNull
    private ShoesDetailsDTO shoesDetails;

    public OrderDetailsDTO(Long id) {
        this.id = id;
    }
}
