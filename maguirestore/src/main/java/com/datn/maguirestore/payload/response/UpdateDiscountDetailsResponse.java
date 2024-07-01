package com.datn.maguirestore.payload.response;

import com.datn.maguirestore.dto.DiscountDTO;
import com.datn.maguirestore.dto.ShoesDTO;
import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.entity.Shoes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDiscountDetailsResponse {
  private Long id;
  private int status;
  private Discount discountId;
  private Shoes shoesId;

}
