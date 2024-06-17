package com.datn.maguirestore.entity;

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

  @Column(name = "price")
  private Float price;

  @Column(name = "import_price")
  private Float importPrice;

  @Column(name = "tax")
  private Float tax;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private Integer status;

  @ManyToOne
  @JoinColumn(name = "shoes_id")
  private Shoes shoes;

  @ManyToOne
  @JoinColumn(name = "size_id")
  private Size size;

  @ManyToOne
  @JoinColumn(name = "color_id")
  private Color color;

}
