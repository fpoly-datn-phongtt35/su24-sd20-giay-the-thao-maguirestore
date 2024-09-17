package com.datn.maguirestore.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/** nhannxph27197 - ShoesDetails. */
@Entity
@Table(name = "shoes_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoesDetails extends AbstractAuditingEntity<Long> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "import_price")
  private BigDecimal importPrice;

  @Column(name = "tax")
  private BigDecimal tax;

  @Column(name = "quantity")
  private Long quantity;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private Integer status;

  @ManyToOne
  @JoinColumn
  private Shoes shoes;

  @ManyToOne
  @JoinColumn(name = "brand_id")
  private Brand brand;

  @ManyToOne
  @JoinColumn(name = "size_id")
  private Size size;

  @ManyToOne
  @JoinColumn(name = "color_id")
  private Color color;

}
