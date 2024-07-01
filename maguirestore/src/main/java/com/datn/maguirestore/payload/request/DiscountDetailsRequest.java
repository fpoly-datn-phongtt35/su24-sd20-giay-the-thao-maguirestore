package com.datn.maguirestore.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDetailsRequest {
  private Long id;
  private Long discountId;
  private Long shoeId;

}
