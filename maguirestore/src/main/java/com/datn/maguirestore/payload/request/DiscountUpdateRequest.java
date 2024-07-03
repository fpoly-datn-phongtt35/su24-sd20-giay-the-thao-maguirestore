package com.datn.maguirestore.payload.request;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DiscountUpdateRequest {
    private Long id;
    private String code;
    private Integer status;

    @NotBlank(message = "{error.discount.name.not.blank}")
    private String name;

    @NotNull(message = "{error.discount.method.not.null}")
    private Integer discountMethod;

    private Instant startDate;
    private Instant endDate;

    private BigDecimal discountAmount;

}
