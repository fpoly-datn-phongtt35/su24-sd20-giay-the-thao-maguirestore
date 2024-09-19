package com.datn.maguirestore.service.dto2;

import com.datn.maguirestore.util.DataUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
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

    @NotEmpty(message = "{error.discount.details.not.empty}")
    private List<DiscountShoesDetailsDTO> discountShoesDetailsDTOS;

    public Instant getStartDate() {
        return DataUtils.toInstant(startDate);
    }

    public Instant getEndDate() {
        return DataUtils.toInstant(endDate);
    }
}
