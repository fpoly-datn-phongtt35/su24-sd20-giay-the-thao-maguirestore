package com.datn.maguirestore.dto;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchResDTO {

  private Long id;
  private String code;
  private Long idCustomer;
  private String customer;
  private String phone;
  private String receivedBy;
  private BigDecimal totalPrice;
  private Integer status;
  private Instant createdDate;
  private String lastModifiedBy;
}