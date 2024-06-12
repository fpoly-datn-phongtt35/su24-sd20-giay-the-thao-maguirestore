package com.datn.maguirestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "discount_shoes_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountShoesDetails extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "discount_amount", precision = 21, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    private Discount discount;

    @ManyToOne
    private Shoes shoesDetails;

    @Column(name = "brand_id")
    private Long brandId;
}
