package com.datn.maguirestore.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDiscountDetailsRequest {
  private int status;
  private Long discountId;
  private Long shoesId;
}
