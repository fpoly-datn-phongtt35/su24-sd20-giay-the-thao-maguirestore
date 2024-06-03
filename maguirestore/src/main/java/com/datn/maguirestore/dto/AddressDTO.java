package com.datn.maguirestore.dto;

import java.io.Serializable;
import java.time.Instant;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AddressDTO implements Serializable {

  private Long id;

  private Integer province;
  private String provinceName;

  private Integer district;
  private String districtName;

  private Integer ward;
  private String wardName;

  private String addressDetails;

  private Integer status;

  private String createdBy;

  private Instant createdDate;

  private String lastModifiedBy;

  private Instant lastModifiedDate;
}
