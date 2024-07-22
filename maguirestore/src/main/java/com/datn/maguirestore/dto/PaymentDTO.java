package com.datn.maguirestore.dto;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

  private Long id;

  private String code;

  private Integer paymentMethod;

  private Integer paymentStatus;

  private Integer status;

  private String createdBy;

  private Instant createdDate;

  private String lastModifiedBy;

  private Instant lastModifiedDate;

  @Override
  public String toString() {
    return "PaymentDTO{" +
        "id=" + getId() +
        ", code='" + getCode() + "'" +
        ", paymentMethod=" + getPaymentMethod() +
        ", paymentStatus=" + getPaymentStatus() +
        ", status=" + getStatus() +
        ", createdBy='" + getCreatedBy() + "'" +
        ", createdDate='" + getCreatedDate() + "'" +
        ", lastModifiedBy='" + getLastModifiedBy() + "'" +
        ", lastModifiedDate='" + getLastModifiedDate() + "'" +
        "}";
  }
}
