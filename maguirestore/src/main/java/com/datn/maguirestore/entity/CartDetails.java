package com.datn.maguirestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/** sonnsph26215 - CartDetails. */
@Entity
@Table(name = "cart_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetails extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "shoes_details_id")
    private ShoesDetails shoesDetails;
}
