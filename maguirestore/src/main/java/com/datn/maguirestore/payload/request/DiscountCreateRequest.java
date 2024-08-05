package com.datn.maguirestore.payload.request;

import com.datn.maguirestore.util.DataUtils;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCreateRequest {

    //private Long id;

    private String code;

    @NotBlank(message = "{error.discount.name.not.blank}")
    private String name;

    @NotNull(message = "{error.discount.startDate.not.null}")
    private String startDate;

    @NotNull(message = "{error.discount.endDate.not.null}")
    private String endDate;

    private BigDecimal discountAmount;

    @NotNull(message = "{error.discount.method.not.null}")
    private Integer discountMethod;

    private Integer discountStatus;

    public Instant getStartDate() {
        return DataUtils.parseToInstant(startDate);
    }

    public Instant getEndDate() {
        return DataUtils.parseToInstant(endDate);
    }
}
