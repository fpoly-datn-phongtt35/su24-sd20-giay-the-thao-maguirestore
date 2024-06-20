package com.datn.maguirestore.payload.request;

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

    private Long id;

    private String code;

    @NotBlank(message = "{error.discount.name.not.blank}")
    private String name;

    @NotNull(message = "{error.discount.method.not.null}")
    private Integer discountMethod;

    private Integer discountStatus;

}
