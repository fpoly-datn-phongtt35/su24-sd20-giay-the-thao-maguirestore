package com.datn.maguirestore.dto;

import com.datn.maguirestore.util.DataUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCreateDTO {

    private Long id;

    private String code;

    @NotBlank(message = "{error.discount.name.not.blank}")
    private String name;

    @NotNull(message = "{error.discount.startDate.not.null}")
    private String startDate;

    @NotNull(message = "{error.discount.endDate.not.null}")
    private String endDate;

    @NotNull(message = "{error.discount.method.not.null}")
    private Integer discountMethod;

    private BigDecimal discountAmount;

    private Integer discountStatus;

//    @NotEmpty(message = "{error.discount.details.not.empty}")
//    private List<DiscountShoesDetailsDTO> discountShoesDetailsDTOS;

    public Instant getStartDate() {
        return DataUtils.getStartOfDay_yyyy_MM_dd_HH_mm_ss(startDate);
    }

    public Instant getEndDate() {
        return DataUtils.getEndOfDay_yyyy_MM_dd_HH_mm_ss(endDate);
    }
}
