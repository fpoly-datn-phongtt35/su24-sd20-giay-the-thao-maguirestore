package com.datn.maguirestore.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** thanhnvph25467 - Address. */
@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address extends AbstractAuditingEntity<Long> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "province")
  private Integer province;

  @Column(name = "province_name")
  private String provinceName;

  @Column(name = "district")
  private Integer district;

  @Column(name = "district_name")
  private String districtName;

  @Column(name = "ward")
  private Integer ward;

  @Column(name = "ward_name")
  private String wardName;

  @Column(name = "address_details")
  private String addressDetails;

  @Column(name = "status")
  private Integer status;
}
